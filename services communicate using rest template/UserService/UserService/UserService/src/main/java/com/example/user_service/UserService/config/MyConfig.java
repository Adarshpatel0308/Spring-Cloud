package com.example.user_service.UserService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

    //creation of the bean here to get the rating of the user form the rating service
    // instead of to create the bean in the user application class
    @Bean
    @LoadBalanced  //use this template to inform the rest template for using the name of the service
                   // at the time of api call instead of host or port
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
