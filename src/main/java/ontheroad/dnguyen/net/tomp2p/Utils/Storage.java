package ontheroad.dnguyen.net.tomp2p.Utils;

import net.tomp2p.dht.StorageLayer;

import net.tomp2p.dht.StorageMemory;
import net.tomp2p.peers.Number640;
import net.tomp2p.rpc.DigestInfo;
import net.tomp2p.storage.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

public class Storage extends StorageLayer {
    private static final Logger LOGGER = LogManager.getLogger(Storage.class);

    public enum StorageMemoryPutMode {
        /** the normal behavior, where each 'put' is checked for version conflicts */
        STANDARD,

        /** Every request to store will fail and returns a {@link net.tomp2p.dht.StorageLayer.PutStatus#FAILED} */
        DENY_ALL
    }

    public enum StorageMemoryGetMode {
        /** the get normal behavior */
        STANDARD,

        /** returns a given value */
        MANIPULATED
    }

    private NavigableMap<Number640, Data> manipulatedMap;

    private StorageMemoryPutMode putMode;
    private StorageMemoryGetMode getMode;

    public Storage() {
        super(new StorageMemory());
        this.putMode = StorageMemoryPutMode.STANDARD;
        this.getMode = StorageMemoryGetMode.STANDARD;
    }

    public void setPutMode(StorageMemoryPutMode mode) {
        assert mode != null;
        this.putMode = mode;
    }

    public void setGetMode(StorageMemoryGetMode mode) {
        assert mode != null;
        this.getMode = mode;
    }

    public void setManipulatedMap(NavigableMap<Number640, Data> manipulatedMap) {
        this.manipulatedMap = manipulatedMap;
    }

    @Override
    public Enum<?> put(Number640 key, Data newData, PublicKey publicKey, boolean putIfAbsent, boolean domainProtection,
                       boolean sendSelf) {
        switch (putMode) {
            case STANDARD: {
                return super.put(key, newData, publicKey, putIfAbsent, domainProtection, sendSelf);
            }
            case DENY_ALL: {
                // LOGGER.warn("Memory mode is denying the put request.");
                return PutStatus.FAILED;
            }
            default: {
                LOGGER.error("Invalid mode {}. Returning a failure.", putMode);
                return PutStatus.FAILED;
            }
        }
    }

    @Override
    public Map<Number640, Enum<?>> putAll(NavigableMap<Number640, Data> dataMap, PublicKey publicKey, boolean putIfAbsent,
                                          boolean domainProtection, boolean sendSelf) {
        switch (putMode) {
            case STANDARD: {
                return super.putAll(dataMap, publicKey, putIfAbsent, domainProtection, sendSelf);
            }
            case DENY_ALL: {
                // LOGGER.warn("Memory mode is denying the put request.");
                return buildReturnMap(dataMap.keySet(), PutStatus.FAILED);
            }
            default: {
                LOGGER.error("Invalid mode {}. Returning a failure.", putMode);
                return buildReturnMap(dataMap.keySet(), PutStatus.FAILED);
            }
        }
    }

    private Map<Number640, Enum<?>> buildReturnMap(Set<Number640> keys, PutStatus status) {
        HashMap<Number640, Enum<?>> result = new HashMap<>(keys.size());
        for (Number640 key : keys) {
            result.put(key, status);
        }
        return result;
    }

    public NavigableMap<Number640, Data> getLatestVersion(Number640 key) {
        switch (getMode) {
            case STANDARD: {
                return super.getLatestVersion(key);
            }
            case MANIPULATED: {
                return manipulatedMap;
            }
            default: {
                LOGGER.error("Invalid mode {}. Returning null.", getMode);
                return null;
            }
        }
    }

    @Override
    public DigestInfo digest(Number640 from, Number640 to, int limit, boolean ascending) {
        switch (getMode) {
            case STANDARD: {
                return super.digest(from, to, limit, ascending);
            }
            case MANIPULATED: {
                DigestInfo digestInfo = new DigestInfo();
                for (Map.Entry<Number640, Data> entry : manipulatedMap.entrySet()) {
                    digestInfo.put(entry.getKey(), entry.getValue().basedOnSet());
                }
                return digestInfo;
            }
            default: {
                LOGGER.error("Invalid mode {}. Returning null.", getMode);
                return null;
            }
        }
    }

}
