package com.example.demo;

import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.accounts.CreditCardAccount;
import com.example.demo.model.accounts.SavingsAccount;
import com.example.demo.model.secondary.Address;
import com.example.demo.model.secondary.Name;
import com.example.demo.model.security.Role;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import com.example.demo.service.interfaces.accounts.CreditCardAccountServiceInterface;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import com.example.demo.service.interfaces.users.ThirdPartyServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*
	FOR TESTING: --------------------------------------------------------------
	In test: @ActiveProfiles("test")

	@Autowired
	public Environment environment;

	if(!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
	}
	---------------------------------------------------------------------------
	 */

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(
			RoleServiceInterface roleService,
			UserServiceInterface userService,
			AccountHolderServiceInterface aHolderService,
			ThirdPartyServiceInterface tPartyService,
			SavingsAccountServiceInterface savingsService,
			CreditCardAccountServiceInterface cCardService,
			CheckingAccountServiceInterface checkingService
	) {
		return args -> {
			// ROLES:
			roleService.createRole(new Role("ROLE_ADMIN"));
			roleService.createRole(new Role("ROLE_ACCOUNTHOLDER"));

			// USERS:
			// - admin users
			userService.createUser(new User(new Name("Antonio", "Sanchez"), "antoniosanchez", "1234"));
			// - account holders
			AccountHolder aH1 = new AccountHolder(new Name("Marta", "Lopez"), "martalopez", "1234", "1990-04-22", new Address("Villarias 10", "Bilbao", "Spain", "48888"));
			AccountHolder aH2 = new AccountHolder(new Name("Lara", "Carreras"), "larita22", "lara26", "2000-10-03", new Address("Acacias 4", "Barcelona", "Spain", "48888"), new Address("Acacias 4", "Barcelona", "Spain", "48888"));
			aHolderService.createAccountHolder(aH1);
			aHolderService.createAccountHolder(aH2);
			// - third parties
			tPartyService.createThirdParty(new ThirdParty("Paola321", "1111"));

			// ACCOUNTS
			// - savings accounts
			savingsService.createSavingsAccount( new SavingsAccount("3210", aH2, null, new BigDecimal("10000"), Currency.getInstance("EUR")) );
			// - credit card account
			cCardService.createCreditCardAccount( new CreditCardAccount("abcd", aH1, null, new BigDecimal("600"), new BigDecimal("1000"), Currency.getInstance("EUR")) );
			// - checking accounts
			// -----> regular (account holder 1 is over 24)
			checkingService.createCheckingAccount( new CheckingAccount("1234", aH1, aH2, new BigDecimal("250"), Currency.getInstance("EUR")) );
			// -----> student (account holder 2 is not over 24)
			checkingService.createCheckingAccount( new CheckingAccount("1234", aH2, null, new BigDecimal("100"), Currency.getInstance("EUR")) );

		};
	}

}
