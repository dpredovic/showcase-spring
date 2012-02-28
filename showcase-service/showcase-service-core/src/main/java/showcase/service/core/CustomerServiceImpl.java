package showcase.service.core;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
@Transactional
@Validated(CreateGroup.class)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public CreateCustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
        Customer customer = mapper.map(requestDto.getCustomer(), Customer.class);
        customer = customerRepository.save(customer);

        for (ContactDto contactDto : requestDto.getContacts()) {
            Contact contact = mapper.map(contactDto, Contact.class);
            contact.setCustomer(customer);
            contactRepository.save(contact);
        }
        CreateCustomerResponseDto response = new CreateCustomerResponseDto();
        response.setId(customer.getId());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getById(long id) {
        Customer customer = customerRepository.findOne(id);
        if (customer == null) {
            return null;
        }
        CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
        return customerDto;
    }

}
