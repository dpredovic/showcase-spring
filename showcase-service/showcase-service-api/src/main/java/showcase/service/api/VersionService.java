package showcase.service.api;

import javax.jws.WebService;

@WebService(serviceName = "ContactService")
public interface VersionService {

	String JNDI_NAME = VersionData.jndiName(VersionService.class);

	String getVersion();

}
