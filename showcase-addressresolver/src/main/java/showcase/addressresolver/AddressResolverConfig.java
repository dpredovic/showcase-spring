package showcase.addressresolver;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import showcase.common.cache.CachingConfig;

@Configuration
@ComponentScan(basePackageClasses = AddressResolverConfig.class)
@Import(CachingConfig.class)
@EnableAsync
public interface AddressResolverConfig {

}
