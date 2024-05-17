package com.mounanga.enterprise;

import com.mounanga.enterprise.users.entity.Role;
import com.mounanga.enterprise.users.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@SpringBootApplication
public class EnterpriseApplication {


    private static final Logger log = LoggerFactory.getLogger(EnterpriseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(RoleRepository roleRepository) {
        return args -> {
            log.info("Role initialisation started");
            if(roleRepository.findAll().isEmpty()) {
                roleRepository.save(new Role("ADMIN"));
                roleRepository.save(new Role("USER"));
                roleRepository.save(new Role("MODERATOR"));
                log.info("Role initialisation completed");
            }
        };
    }

}
