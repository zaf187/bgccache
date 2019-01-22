package com.cache.bgc.model;

import com.cache.bgc.exceptions.CacheValueNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ObjectCache {
    private static final Logger logger = LogManager.getLogger(ObjectCache.class);
    private HashMap<String, ObjectEntry> cache;

    public ObjectCache() {
        cache = new HashMap<>();
    }

    public ObjectEntry getValue(String key) {
        synchronized (cache) {
            if (cache.containsKey(key)) {
                logger.debug("Fetching value for key:[{}]",key);
                ObjectEntry retrievedObject = cache.get(key);
                retrievedObject.setLastFetchedAt(LocalDateTime.now(Clock.systemUTC()));
                return retrievedObject;
            } else {
                throw new CacheValueNotFoundException(String.format("The key:[%s] was not found in the cache",key), null);
            }
        }
    }

    public ObjectEntry putValue(String key, Object value) {
        logger.debug("Putting value:[{}], for key:[{}]", value, key);
        synchronized (cache) {
            if (cache.containsKey(key)) {
                logger.debug("Value already exists, updating value in cache");
                ObjectEntry valueInCache = (ObjectEntry) cache.get(key);
                valueInCache.setObjectValue(value);
                valueInCache.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
                valueInCache.incrementVersion();
                cache.put(key, valueInCache);
                return valueInCache;
            } else {
                logger.debug("Value does not exist, adding to the cache");
                ObjectEntry newValueForCache = new ObjectEntry(key, value);
                cache.put(key, newValueForCache);
                return newValueForCache;
            }
        }
    }

    public HashMap<String, ObjectEntry> getCache() {
        synchronized (this.cache) {
            return cache;
        }
    }

    public void setCache(HashMap<String, ObjectEntry> cache) {
        synchronized (this.cache) {
            this.cache = cache;
        }
    }
}
