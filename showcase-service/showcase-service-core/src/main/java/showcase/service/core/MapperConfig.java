package showcase.service.core;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CustomerDto;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(CustomerDto.class, Customer.class)
                        .fields("properties", "properties");
            }
        });
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(ContactDto.class, Contact.class)
                        .fields("customerId", "customer.id")
                        .fields("communications", "communications");
            }
        });
        return mapper;
    }

}
