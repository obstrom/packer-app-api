package com.obstrom.packerservice.config;

import com.github.skjolber.packing.visualizer.packaging.DefaultPackagingResultVisualizerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DefaultPackagingResultVisualizerFactory getVisualizerFactory() {
        return new DefaultPackagingResultVisualizerFactory();
    }

}
