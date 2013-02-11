package showcase.persistence.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import showcase.persistence.unit.PersistenceUnitConfig;

@Configuration
@EnableTransactionManagement
@Import(PersistenceUnitConfig.class)
@EnableJpaRepositories
public class RepositoryConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

}
