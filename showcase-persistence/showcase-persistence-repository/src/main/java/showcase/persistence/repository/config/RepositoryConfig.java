package showcase.persistence.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import showcase.persistence.unit.config.PersistenceUnitConfig;

@Configuration
@EnableTransactionManagement
@Import(PersistenceUnitConfig.class)
@ImportResource({"classpath:repositoryContext.xml"})
public class RepositoryConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

}
