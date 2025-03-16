package com.eazybytes.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(AuditAwareImpl.AUDITOR)
public class AuditAwareImpl implements AuditorAware<String> {
    public static final String AUDITOR = "AuditAwareImpl";
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Accounts_MS");
    }
}
