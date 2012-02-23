package showcase.service.remoting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import showcase.service.core.config.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
@PropertySource("file:showcase.properties")
public interface EjbConfig {

}
