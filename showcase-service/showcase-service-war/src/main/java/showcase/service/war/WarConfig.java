package showcase.service.war;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import showcase.service.core.ServiceConfig;
import showcase.service.remoting.ContactServiceBean;
import showcase.service.remoting.CustomerServiceBean;
import showcase.service.remoting.RestServiceBean;

@Configuration
@Import(ServiceConfig.class)
@ImportResource("/WEB-INF/cxfContext.xml")
@PropertySource("classpath:showcase.properties")
public class WarConfig {

    @Bean
    public RestServiceBean restServiceBean() {
        return new RestServiceBean();
    }

    @Bean
    public ContactServiceBean contactServiceBean() {
        return new ContactServiceBean();
    }

    @Bean
    public CustomerServiceBean customerServiceBean() {
        return new CustomerServiceBean();
    }

}
