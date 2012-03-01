package showcase.service.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import showcase.addressresolver.config.AddressResolverConfig;
import showcase.common.cache.CachingConfig;
import showcase.persistence.repository.config.RepositoryConfig;
import showcase.service.core.CustomerServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = CustomerServiceImpl.class)
@Import({RepositoryConfig.class, AddressResolverConfig.class, CachingConfig.class})
@EnableAspectJAutoProxy
public class ServiceConfig {

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
