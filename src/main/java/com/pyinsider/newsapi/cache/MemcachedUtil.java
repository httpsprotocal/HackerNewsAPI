package com.pyinsider.newsapi.cache;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MemcachedUtil {

    @Value("${memcached.addresses}")
    private String memcachedAddresses;

    @Bean
    public MemcachedClient memcached() throws IOException {
        return new MemcachedClient(
                new ConnectionFactoryBuilder()
                        .setTranscoder(new SerializingTranscoder())
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                        .build(),
                AddrUtil.getAddresses(memcachedAddresses));
    }

}
