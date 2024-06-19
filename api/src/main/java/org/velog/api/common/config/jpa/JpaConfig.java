package org.velog.api.common.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.velog.db")
@EnableJpaRepositories(basePackages = "org.velog.db")
public class JpaConfig {
}
