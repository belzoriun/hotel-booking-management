package com.hotelbooking.backend;

import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.data.stream.memory.MemoryDataStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DataStream getStream() {
        return new MemoryDataStream();
    }
}
