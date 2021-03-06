package showcase.service.ear;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.CustomerDto;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoteInvocation {

	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {
		Properties jndiProperties = new Properties();
		jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		@Cleanup
		Context context = new InitialContext(jndiProperties);

		customerService = (CustomerService) context.lookup(CustomerService.JNDI_NAME);
	}

	@Test
	public void testServiceCall() throws Exception {
		long id = 421L;
		CustomerDto customer = customerService.getById(id);
		assertThat(customer.getId()).isEqualTo(id);
	}
}
