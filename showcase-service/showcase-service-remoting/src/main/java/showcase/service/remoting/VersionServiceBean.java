package showcase.service.remoting;

import showcase.service.api.VersionData;
import showcase.service.api.VersionService;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jws.WebService;

//EJB-component
@Singleton(name = "VersionService")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
//EJB-remoting
@WebService(name = "VersionService")
@Remote(VersionService.class)
public class VersionServiceBean implements VersionService {

	@Override
	public String getVersion() {
		return VersionData.IMPLEMENTATION_VERSION;
	}

}
