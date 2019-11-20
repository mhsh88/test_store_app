package ir.sharifi.soroush.soroush_test_project.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("loadUserByUsername",
                CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build()
                .asMap(),
                true));


        ConcurrentMap<Object, Object> store = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build()
                .asMap();

        caches.add(new ConcurrentMapCache("getAllUser", store,true));
        caches.add(new ConcurrentMapCache("getAllFood", store,true));
        caches.add(new ConcurrentMapCache("getAllDetergent", store,true));

        cacheManager.setCaches(caches);
        return cacheManager;

    }
}
