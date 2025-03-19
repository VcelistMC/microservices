package com.eazybytes.accounts;

import com.eazybytes.accounts.audit.AuditAwareImpl;
import com.eazybytes.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(value = {AccountsContactInfoDTO.class})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = AuditAwareImpl.AUDITOR)
@OpenAPIDefinition(
	info = @Info(
			title = "Account Micorservice API definition"
	)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
