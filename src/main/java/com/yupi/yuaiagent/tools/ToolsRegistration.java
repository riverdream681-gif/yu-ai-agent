package com.yupi.yuaiagent.tools;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolsRegistration {

    @Value("${app.config.security-key}")
    private String searchApi;


    @Bean
    public ToolCallback[] allTools() {

        WeatherTools weatherTools = new WeatherTools();


        return ToolCallbacks.from(
            weatherTools
        );
    }


}
