package showcase.service.core;

import ma.glasnost.orika.MapperFacade;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.repository.CustomerRepository;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CreateCustomerResponseDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.api.validation.CreateGroup;
import showcase.service.core.exceptionmapping.ExceptionsMapped;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Transactional
@Validated(CreateGroup.class)
@ExceptionsMapped
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerRepository customerRepository;

	@Inject
	private ContactRepository contactRepository;

	@Inject
	private MapperFacade mapper;

	@Override
	public CreateCustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
		Customer customer = mapper.map(requestDto.getCustomer(), Customer.class);
		customer = customerRepository.save(customer);

		for (ContactDto contactDto : requestDto.getContacts()) {
			Contact contact = mapper.map(contactDto, Contact.class);
			contact.setCustomer(customer);
			contactRepository.save(contact);
		}
		return new CreateCustomerResponseDto(customer.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerDto getById(long id) {
		Customer customer = customerRepository.findOne(id);
		if (customer == null) {
			return null;
		}
		return mapper.map(customer, CustomerDto.class);
	}

}
