package com.saferide.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * audit 설정
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}
