package com.analog.alex.messages.configs

import java.util.*
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.cache.CacheConfig
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class RedisConfiguration(
    @Value("\${spring.redis.host}") val host: String,
    @Value("\${spring.redis.port}") val port: String
) {

    @Bean(destroyMethod = "shutdown")
    fun redisson(): RedissonClient {
        val config = Config().apply {
            useSingleServer().address = "redis://$host:$port"
        }
        return Redisson.create(config)
    }

    @Bean
    fun cacheManager(redissonClient: RedissonClient?): CacheManager {
        val config: MutableMap<String, CacheConfig> = HashMap<String, CacheConfig>()
        config["message_record"] = CacheConfig(24 * 60 * 1000, 12 * 60 * 1000)
        return RedissonSpringCacheManager(redissonClient, config)
    }
}
