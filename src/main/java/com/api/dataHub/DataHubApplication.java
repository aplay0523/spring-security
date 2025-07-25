package com.api.dataHub;

import com.api.dataHub.common.SwaggerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataHubApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataHubApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataHubApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<SwaggerFilter> filterRegistrationBean() {
		FilterRegistrationBean<SwaggerFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new SwaggerFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> {
			logger.info("API 서버 실행 완료");
		};
	}
}
