package showcase.service.war;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import showcase.service.api.ContactService;
import showcase.service.api.CustomerService;
import showcase.service.core.ServiceConfig;
import showcase.service.remoting.RestServiceBean;

import javax.inject.Inject;

@Configuration
@Import(ServiceConfig.class)
@PropertySource("classpath:showcase.properties")
@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class WarConfig {

	@Inject
	private CustomerService customerService;
	@Inject
	private ContactService contactService;
	@Inject
	private Bus cxfBus;

	@Bean
	public Server customerWsServer() {
		JaxWsServerFactoryBean serverFactoryBean = new JaxWsServerFactoryBean();
		serverFactoryBean.setAddress("/CustomerService");
		serverFactoryBean.setServiceBean(customerService);
		serverFactoryBean.setServiceClass(CustomerService.class);
		serverFactoryBean.setBus(cxfBus);
		return serverFactoryBean.create();
	}

	@Bean
	public Server contactWsServer() {
		JaxWsServerFactoryBean serverFactoryBean = new JaxWsServerFactoryBean();
		serverFactoryBean.setAddress("/ContactService");
		serverFactoryBean.setServiceBean(contactService);
		serverFactoryBean.setServiceClass(ContactService.class);
		serverFactoryBean.setBus(cxfBus);
		return serverFactoryBean.create();
	}

	@Bean
	public Server restServer() {
		JAXRSServerFactoryBean serverFactoryBean = new JAXRSServerFactoryBean();
		serverFactoryBean.setAddress("/rest");
		serverFactoryBean.setServiceBean(restServiceBean());
		serverFactoryBean.setBus(cxfBus);
		return serverFactoryBean.create();
	}

	@Bean
	public RestServiceBean restServiceBean() {
		return new RestServiceBean();
	}
}
