package com.projectTutoriel.AppUser;

import com.projectTutoriel.AppUser.entities.AppUser;
import com.projectTutoriel.AppUser.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AppUserApplication{

	@Autowired
	AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder getbBcpe(){

		return new BCryptPasswordEncoder();
	}

}
