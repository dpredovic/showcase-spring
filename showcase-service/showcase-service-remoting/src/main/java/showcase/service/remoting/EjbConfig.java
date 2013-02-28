package showcase.service.remoting;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import showcase.service.core.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
@PropertySource("file:showcase.properties")
public class EjbConfig {

}
