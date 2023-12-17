package com.petersokor.TimeSlotManager;

import com.petersokor.TimeSlotManager.controller.LogIn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.petersokor.TimeSlotManager")
public class TimeSlotManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimeSlotManagerApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer configurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addViewControllers(ViewControllerRegistry registry) {
//				registry.addViewController("/").setViewName("index");
//			}
//		};
//	}

	@Bean


	public ServletRegistrationBean myServletRegistration() {
		ServletRegistrationBean registrationBean =
				new ServletRegistrationBean(new LogIn(), "/login");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
}

