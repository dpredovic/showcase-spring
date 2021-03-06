package showcase.service.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import showcase.addressresolver.AddressResolverConfig;
import showcase.common.cache.CachingConfig;
import showcase.persistence.repository.RepositoryConfig;

import javax.validation.ValidatorFactory;

@Configuration
@ComponentScan
@Import({RepositoryConfig.class, AddressResolverConfig.class, CachingConfig.class})
@EnableAspectJAutoProxy
public class ServiceConfig {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
		methodValidationPostProcessor.setValidatorFactory(localValidatorFactoryBean());
		return methodValidationPostProcessor;
	}

	@Bean
	public ValidatorFactory localValidatorFactoryBean() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setMessageInterpolator(new SimpleAnnotationNameMessageInterpolator());
		return localValidatorFactoryBean;
	}

}
