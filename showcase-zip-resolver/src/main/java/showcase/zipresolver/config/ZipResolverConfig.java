package showcase.zipresolver.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import showcase.common.cache.CachingConfig;

@Configuration
@ComponentScan("showcase.zipresolver")
@Import(CachingConfig.class)
public class ZipResolverConfig {

}
