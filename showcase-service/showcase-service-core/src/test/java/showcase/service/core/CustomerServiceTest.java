package showcase.service.core;

import java.util.Set;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.core.config.ServiceConfig;
import showcase.zipresolver.ZipResolver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = ServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TestCustomerCreator customerCreator;

    @Test
    public void createCustomer() {
        CustomerDto customer = customerCreator.createCustomer();
        assertThat(customer.getId()).isNotNull();

        CustomerDto found = customerService.getById(customer.getId());

        assertThat(found).isNotNull();
        assertThat(found).isEqualTo(customer);
    }

    @Test
    public void createCustomerFailsValidation() {
        CreateCustomerRequestDto request = customerCreator.createRequest("");
        request.getCustomer().setCooperationPartnerId(null);
        try {
            customerService.createCustomer(request);
            fail();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(MethodConstraintViolationException.class);
            MethodConstraintViolationException cve = (MethodConstraintViolationException) e;
            Set<MethodConstraintViolation<?>> constraintViolations = cve.getConstraintViolations();
            assertThat(constraintViolations).hasSize(1);
        }
    }

    @Bean
    public ZipResolver zipResolver() {
        return Mockito.mock(ZipResolver.class, RETURNS_SMART_NULLS);
    }

}
