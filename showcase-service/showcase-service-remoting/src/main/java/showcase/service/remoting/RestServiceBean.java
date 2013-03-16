package showcase.service.remoting;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import showcase.service.api.ContactService;
import showcase.service.api.CustomerService;
import showcase.service.api.VersionService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CreateCustomerResponseDto;
import showcase.service.api.dto.CustomerDto;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/")
public class RestServiceBean {

	@Inject
	private CustomerService customerService;
	@Inject
	private ContactService contactService;
	@Inject
	private VersionService versionService;

	@GET
	@Path("/contact/{id}")
	public ContactDto getContact(@PathParam("id") long contactId) {
		return contactService.getContact(contactId);
	}

	@GET
	@Path("/customer/{id}/contact/type/{type}")
	public ContactDto getContactByCustomerAndType(@PathParam("id") long customerId, @PathParam("type") String type) {
		return contactService.getContactByCustomerAndType(customerId, type);
	}

	@GET
	@Path("/customer/{id}/contact")
	public List<ContactDto> getContactsByCustomer(@PathParam("id") long customerId) {
		return contactService.getContactsByCustomer(customerId);
	}

	@PUT
	@Path("/customer")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CreateCustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
		return customerService.createCustomer(requestDto);
	}

	@GET
	@Path("/customer/{id}")
	public CustomerDto getById(@PathParam("id") long id) {
		return customerService.getById(id);
	}

	@GET
	@Path("/version")
	public String getVersion() {
		return versionService.getVersion();
	}

}
