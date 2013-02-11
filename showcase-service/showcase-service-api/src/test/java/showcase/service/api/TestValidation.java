package showcase.service.api;

import org.junit.BeforeClass;
import org.junit.Test;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;
import showcase.service.api.type.CustomerType;
import showcase.service.api.type.DispatchType;
import showcase.service.api.validation.CreateGroup;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class TestValidation {

    private static Validator validator;

    @BeforeClass
    public static void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void testValid() {
        CreateCustomerRequestDto request = createValidRequest();

        Set<ConstraintViolation<CreateCustomerRequestDto>> violations = validator.validate(request, CreateGroup.class);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testInvalid() {
        CreateCustomerRequestDto request = createValidRequest();
        request.getCustomer().setCooperationPartnerId(null);

        Set<ConstraintViolation<CreateCustomerRequestDto>> violations = validator.validate(request, CreateGroup.class);

        assertThat(violations).hasSize(1);
        ConstraintViolation<CreateCustomerRequestDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("customer.cooperationPartnerId");
    }

    @Test
    public void testInvalidEnum() {
        CreateCustomerRequestDto request = createValidRequest();
        request.getCustomer().setCustomerType("12345");

        Set<ConstraintViolation<CreateCustomerRequestDto>> violations = validator.validate(request, CreateGroup.class);

        assertThat(violations).hasSize(1);
        ConstraintViolation<CreateCustomerRequestDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("customer.customerType");
    }

    private CreateCustomerRequestDto createValidRequest() {

        CustomerDto customer = new CustomerDto();
        customer.setCustomerType(CustomerType.PERSON.toString());
        customer.setDispatchType(DispatchType.EMAIL.toString());
        customer.setCooperationPartnerId(1L);
        customer.setRegistrationDate(new Date(new Date().getTime() - 10));

        ContactDto standardContact = createContact(ContactType.STANDARD);
        ContactDto untypedContact = createContact(null);

        return new CreateCustomerRequestDto(customer, Arrays.asList(standardContact, untypedContact));
    }

    private ContactDto createContact(ContactType contactType) {
        ContactDto contact = new ContactDto();
        if (contactType != null) {
            contact.setContactType(contactType.toString());
        }
        contact.setFirstName("firstName");
        contact.setLastName("lastName");
        contact.setStreet("street");
        contact.setZipCode("zipCode");
        contact.setCountryCode("cc");

        contact.getCommunications().put(CommunicationType.EMAIL.toString(), "test@test");
        return contact;
    }
}
