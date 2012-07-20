package showcase.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.addressresolver.AddressResolver;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CreateCustomerResponseDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.validation.AllKeysInEnum;

import java.util.Collection;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = ServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class CustomerServiceTest {

    @Inject
    private CustomerService customerService;

    @Inject
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
    public void createCustomerFailsSimpleValidation() {
        CreateCustomerRequestDto request = customerCreator.createRequest("");
        request.getCustomer().setCooperationPartnerId(null);

        CreateCustomerResponseDto response = customerService.createCustomer(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNull();
        Collection<ValidationErrorDto> validationErrors = response.getValidationErrors();
        assertThat(validationErrors).hasSize(1);
        ValidationErrorDto validationError = validationErrors.iterator().next();
        assertThat(validationError.getPropertyPath()).isEqualTo("customer.cooperationPartnerId");
        assertThat(validationError.getMessage()).isEqualTo(NotNull.class.getSimpleName());
    }

    @Test
    public void createCustomerFailsMapValidation() {
        CreateCustomerRequestDto request = customerCreator.createRequest("");
        ContactDto contact = request.getContacts().iterator().next();
        contact.getCommunications().clear();
        contact.getCommunications().put("dummy", "dummy");

        CreateCustomerResponseDto response = customerService.createCustomer(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNull();
        Collection<ValidationErrorDto> validationErrors = response.getValidationErrors();
        assertThat(validationErrors).hasSize(1);
        ValidationErrorDto validationError = validationErrors.iterator().next();
        assertThat(validationError.getPropertyPath()).isEqualTo("contacts[0].communications");
        assertThat(validationError.getMessage()).isEqualTo(AllKeysInEnum.class.getSimpleName());
    }

    @Bean
    public AddressResolver zipResolver() {
        return Mockito.mock(AddressResolver.class, RETURNS_SMART_NULLS);
    }

}
