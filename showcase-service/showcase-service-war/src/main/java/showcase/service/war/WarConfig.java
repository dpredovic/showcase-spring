package showcase.service.war;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import showcase.service.core.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
@ImportResource("/WEB-INF/cxfContext.xml")
@PropertySource("classpath:showcase.properties")
public interface WarConfig {

}
