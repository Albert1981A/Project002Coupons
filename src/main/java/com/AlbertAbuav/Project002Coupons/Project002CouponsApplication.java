package com.AlbertAbuav.Project002Coupons;

import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Project002CouponsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Project002CouponsApplication.class, args);
		System.out.println("Spring IoC container was loaded!");
	}

}
