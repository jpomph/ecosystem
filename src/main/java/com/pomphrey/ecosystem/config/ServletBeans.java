package com.pomphrey.ecosystem.config;

import org.jfree.chart.servlet.DisplayChart;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletBeans {

    @Bean
    public ServletRegistrationBean MyServlet() {
        return new ServletRegistrationBean<>(new DisplayChart(),"/chart");
    }

}
