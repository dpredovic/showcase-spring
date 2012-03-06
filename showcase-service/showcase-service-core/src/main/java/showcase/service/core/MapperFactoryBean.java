package showcase.service.core;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CustomerDto;

@Component
public class MapperFactoryBean extends AbstractFactoryBean<Mapper> {

    @Override
    public Class<?> getObjectType() {
        return Mapper.class;
    }

    @Override
    protected Mapper createInstance() throws Exception {
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

    @Override
    protected void destroyInstance(Mapper instance) throws Exception {
        ((DozerBeanMapper) instance).destroy();
    }
}
