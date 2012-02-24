package showcase.service.remoting;

import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

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
    public List<ContactDto> getContactsByCustomer(long customerId) {
        return delegate.getContactsByCustomer(customerId);
    }

    @Override
    public List<ContactDto> getByEmail(String email) {
        return delegate.getByEmail(email);
    }
}
