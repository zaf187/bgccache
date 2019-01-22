package com.cache.bgc.service;

import com.cache.bgc.model.ObjectCache;
import com.cache.bgc.model.ObjectEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheService {
    private ObjectCache cacheMap;
    private Long expiryDuration;

    public CacheService(ObjectCache cacheMap,
                        @Value("${app.cache.expiry.duration}") Long expiryDuration) {
        this.cacheMap = cacheMap;
        this.expiryDuration = expiryDuration;
    }

    public ObjectCache getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(ObjectCache cacheMap) {
        this.cacheMap = cacheMap;
    }

    public Long getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(Long expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public ObjectEntry putValueInCache(String key, Object value) {
        return cacheMap.putValue(key, value);
    }

    public List<ObjectEntry> getAllEntries() {
        return new ArrayList<>(cacheMap.getCache().values());
    }

    public Object getValueFromCache(String key) {
        ObjectEntry retrievedEntry = cacheMap.getValue(key);
        return retrievedEntry.getObjectValue();
    }
}
