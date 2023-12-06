package zipgo.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipgo.common.cache.CacheType;
import zipgo.common.cache.CustomKeyGenerator;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches());
        return cacheManager;
    }

    private List<CaffeineCache> caches() {
        return Arrays.stream(CacheType.values())
                .map(cacheType -> new CaffeineCache(cacheType.getName(), cache(cacheType)))
                .toList();
    }

    private Cache<Object, Object> cache(CacheType cacheType) {
        return Caffeine.newBuilder()
                .maximumSize(cacheType.getMaxSize())
                .expireAfterWrite(cacheType.getExpireTime(), TimeUnit.SECONDS)
                .build();
    }


    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
}
