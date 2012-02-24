package showcase.service.remoting;

import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;

//EJB-component
@Singleton(name = "ContactService")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
//EJB-component Spring bridge
@Interceptors(SpringBeanAutowiringInterceptor.class)
//EJB-remoting
@WebService(name = "ContactService")
@Remote(ContactService.class)
public class ContactServiceBean implements ContactService {

    @Autowired
    private ContactService delegate;

    @Override
    public ContactDto getContact(long contactId) {
        return delegate.getContact(contactId);
    }

    @Override
    public ContactDto getContactByCustomerAndType(long customerId, String type) {
        return delegate.getContactByCustomerAndType(customerId, type);
    }

    @Override
    @GET
    @Path("/customer/{id}/contact")
    public List<ContactDto> getContactsByCustomer(
            @PathParam("id")
            long customerId) {
        return delegate.getContactsByCustomer(customerId);
    }
}
