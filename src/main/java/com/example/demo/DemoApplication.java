package com.example.demo;

import com.example.demo.model.aux.Address;
import com.example.demo.model.aux.Name;
import com.example.demo.model.security.Role;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

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
	CommandLineRunner run(
			UserServiceInterface userService,
			AccountHolderServiceInterface aHolderService,
			RoleServiceInterface roleService
	) {
		return args -> {

			userService.saveUser(new User(new Name("Antonio", "Sanchez"), "antoniosanchez", "1234"));
			aHolderService.saveAccountHolder(new AccountHolder(new Name("Marta", "Lopez"), "martalopez", "1234", "1990-04-22", new Address("Villarias 10", "Bilbao", "Spain", "48888")));
			aHolderService.saveAccountHolder(new AccountHolder(new Name("Lara", "Carreras"), "larita26", "lara26", "1985-10-03", new Address("Acacias 4", "Barcelona", "Spain", "48888")));

			roleService.saveRole(new Role("ROLE_ADMIN"));
			roleService.saveRole(new Role("ROLE_ACCOUNTHOLDER"));

			roleService.addRoleToUser("AntonioSanchez", "ROLE_ADMIN");
			roleService.addRoleToUser("martalopez", "ROLE_ACCOUNTHOLDER");
			roleService.addRoleToUser("larita26", "ROLE_ACCOUNTHOLDER");
		};
	}

}
