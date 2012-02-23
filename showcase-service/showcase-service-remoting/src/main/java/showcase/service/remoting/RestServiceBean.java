package showcase.service.remoting;

import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import showcase.common.ContactType;
import showcase.service.api.ContactService;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("/")
public class RestServiceBean {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContactService contactService;

    @GET
    @Path("/contact/{id}")
    public ContactDto getContact(
            @PathParam("id")
            long contactId) {
        return contactService.getContact(contactId);
    }

    @GET
    @Path("/customer/{id}/contact/type/{type}")
    public ContactDto getContactByCustomerAndType(
            @PathParam("id")
            long customerId,
            @PathParam("type")
            ContactType type) {
        return contactService.getContactByCustomerAndType(customerId, type);
    }

    @GET
    @Path("/customer/{id}/contact")
    public List<ContactDto> getContactsByCustomer(long customerId) {
        return contactService.getContactsByCustomer(customerId);
    }

    @POST
    @Path("/customer")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Long createCustomer(CreateCustomerRequestDto requestDto) {
        return customerService.createCustomer(requestDto);
    }

    @GET
    @Path("/customer/{id}")
    public CustomerDto getById(
            @PathParam("id")
            long id) {
        return customerService.getById(id);
    }

}
