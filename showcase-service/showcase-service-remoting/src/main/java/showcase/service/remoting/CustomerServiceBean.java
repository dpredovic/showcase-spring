package showcase.service.remoting;

import lombok.Delegate;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import showcase.service.api.CustomerService;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

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

	@Inject
	@Delegate
	private CustomerService delegate;

}
