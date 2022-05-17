package com.example.demo;

import com.example.demo.model.security.Role;
import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run( UserServiceInterface userService, RoleServiceInterface roleService ) {
		return args -> {

			userService.saveUser(new User("AntonioSanchez", "1234"));

			roleService.saveRole(new Role("ROLE_ADMIN"));
			roleService.saveRole(new Role("ROLE_ACCOUNTHOLDER"));
			roleService.saveRole(new Role("ROLE_THIRDPARTY"));

			roleService.addRoleToUser("AntonioSanchez", "ROLE_ADMIN");
		};
	}

}
