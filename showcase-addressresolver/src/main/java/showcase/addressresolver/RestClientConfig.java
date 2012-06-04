package showcase.addressresolver;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.spring.JAXRSClientFactoryBeanDefinitionParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integration")
@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class RestClientConfig {

    @Value("${address.resolver.url}")
    private String resolverUrl;

    @Bean
    public JAXRSClientFactoryBean jaxrsClientFactoryBean() {
        JAXRSClientFactoryBean clientFactoryBean = new JAXRSClientFactoryBeanDefinitionParser.JAXRSSpringClientFactoryBean();

        clientFactoryBean.setServiceClass(AddressResolver.class);
        clientFactoryBean.setAddress(resolverUrl);
        return clientFactoryBean;
    }
}
