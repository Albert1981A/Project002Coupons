package com.AlbertAbuav.Project002Coupons;

import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication  // ==> @Configuration + @ComponentScan(basePackages = "com.AlbertAbuav.Project002Coupons") + @ConfigurationScan
@EnableScheduling  // ==> Will execute the "DailyTask"
public class Project002CouponsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Project002CouponsApplication.class, args);
		System.out.println("Spring IoC container was loaded!");
	}

}
