package showcase.service.remoting;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;

//EJB-component
@Singleton(name = "CustomerService")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
//EJB-component Spring bridge
@Interceptors(SpringBeanAutowiringInterceptor.class)
//EJB-remoting
@Remote(CustomerService.class)
@WebService(name = "CustomerService")
public class CustomerServiceBean implements CustomerService {

    @Autowired
    private CustomerService delegate;

    @Override
    public Long createCustomer(CreateCustomerRequestDto requestDto) {
        return delegate.createCustomer(requestDto);
    }

    @Override
    public CustomerDto getById(long id) {
        return delegate.getById(id);
    }
}
