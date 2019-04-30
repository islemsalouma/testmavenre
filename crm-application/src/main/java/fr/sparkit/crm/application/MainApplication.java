package fr.sparkit.crm.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EntityScan("fr.sparkit.crm.entities")
@ComponentScan("fr.sparkit.crm")
@EnableJpaRepositories("fr.sparkit.crm")
public class MainApplication extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        LOG.debug("Starting {} application...", "crm-back-end-java");
        SpringApplication.run(MainApplication.class, args);
    }

}
