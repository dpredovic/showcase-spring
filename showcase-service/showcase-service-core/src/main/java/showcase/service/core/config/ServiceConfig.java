package showcase.service.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import showcase.common.cache.CachingConfig;
import showcase.persistence.repository.config.RepositoryConfig;
import showcase.zipresolver.config.ZipResolverConfig;

@Configuration
@ComponentScan("showcase.service.core")
@Import({RepositoryConfig.class, ZipResolverConfig.class, CachingConfig.class})
public interface ServiceConfig {

}
