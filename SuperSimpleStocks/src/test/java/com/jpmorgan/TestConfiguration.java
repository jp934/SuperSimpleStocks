package com.jpmorgan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(lazyInit = true, basePackages = {
    "com.jpmorgan.services"
})
@PropertySource("classpath:application.properties")
public class TestConfiguration {

}
