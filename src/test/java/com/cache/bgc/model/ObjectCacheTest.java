package com.cache.bgc.model;

import com.cache.bgc.exceptions.CacheValueNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ObjectCacheTest {
    private static final Logger logger = LogManager.getLogger(ObjectCacheTest.class);

    private ObjectCache objectCache;

    @Before
    public void setUp() {
        objectCache = new ObjectCache();
    }

    @Test
    public void checkPutValueIsWorking() {
        assertEquals(0, objectCache.getCache().size());
        objectCache.putValue("String1", "Value1");
        assertEquals(1, objectCache.getCache().size());
    }

    @Test
    public void checkUpdateValueIsWorking() {
        ObjectEntry sampleEntry = new ObjectEntry("String1", "Value1");
        HashMap<String, ObjectEntry> cacheMap = new HashMap<>();
        cacheMap.put("String1", sampleEntry);
        objectCache.setCache(cacheMap);
        assertEquals(1, objectCache.getCache().size());
        assertEquals("Value1", objectCache.getValue("String1").getObjectValue());
        assertEquals(1L, objectCache.getValue("String1").getVersionNumber());
        objectCache.putValue("String1", "Value3");
        assertEquals(1, objectCache.getCache().size());
        assertEquals("Value3", objectCache.getValue("String1").getObjectValue());
        assertEquals(2L, objectCache.getValue("String1").getVersionNumber());
    }

    @Test
    public void checkGetValueIsWorking() {
        ObjectEntry sampleEntry = new ObjectEntry("String1", "Value1");
        HashMap<String, ObjectEntry> cacheMap = new HashMap<>();
        cacheMap.put("String1", sampleEntry);
        LocalDateTime lastUpdated = sampleEntry.getUpdatedAt();
        objectCache.setCache(cacheMap);
        assertEquals(1, objectCache.getCache().size());

        ObjectEntry actualEntry = objectCache.getValue("String1");
        assertEquals(sampleEntry, actualEntry);
    }

    @Test(expected = CacheValueNotFoundException.class)
    public void exceptionThrowsWhenValueIsMissing() {
        assertEquals(0, objectCache.getCache().size());
        objectCache.getValue("String1");
    }

    @Test
    public void testConcurrentWritesDoNotLoseData() {
        assertEquals(0, objectCache.getCache().size());
        IntStream.range(0, 100).forEach( intVal -> {
            CacheThrasher thrasher = new CacheThrasher(objectCache,
                    "string"+intVal%7,
                    "This is message number:"+intVal,
                    30);
            thrasher.run();
        });
        logger.debug("Size of the cache = {}",objectCache.getCache().size());
        assertEquals(7, objectCache.getCache().size());
    }

    class CacheThrasher implements Runnable {
        private ObjectCache cache;
        private String cachekey;
        private Object cacheValue;
        private int iterations;

        public CacheThrasher(ObjectCache cache,
                             String cacheKey,
                             Object cacheValue,
                             int iterations) {
            this.cache = cache;
            this.cachekey = cacheKey;
            this.cacheValue = cacheValue;
            this.iterations = iterations;
        }

        @Override
        public void run() {
            cache.putValue(cachekey, cacheValue);
            for(int x = 0; x <= iterations; x++) {
                cache.putValue(cachekey, cacheValue+", iterations:"+iterations);
            }
        }
    }

}