package showcase.service.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import showcase.persistence.repository.config.RepositoryConfig;

@Configuration
@ComponentScan("showcase.service.core")
@Import(RepositoryConfig.class)
public interface ServiceConfig {

}
