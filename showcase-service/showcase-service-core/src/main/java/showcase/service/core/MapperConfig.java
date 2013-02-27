package showcase.service.core;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CustomerDto;

@Configuration
public class MapperConfig {

	@Bean
	public MapperFacade mapperFacade() {
		return new ConfigurableMapper() {
			@Override
			protected void configure(MapperFactory factory) {
				factory.registerClassMap(factory.classMap(CustomerDto.class, Customer.class).byDefault().toClassMap());
				factory.registerClassMap(factory.classMap(ContactDto.class, Contact.class)
												.field("customerId", "customer.id")
												.byDefault()
												.toClassMap());
			}
		};
	}
}
