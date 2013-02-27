package showcase.persistence.unit;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Configuration
@Profile({"integration", "standalone"})
public class DataSourceConfig {

	@Inject
	private Environment env;

	@Bean(destroyMethod = "close")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("dataSource.driver"));
		dataSource.setUrl(env.getRequiredProperty("dataSource.url"));
		dataSource.setUsername(env.getRequiredProperty("dataSource.username"));
		dataSource.setPassword(env.getRequiredProperty("dataSource.password"));
		dataSource.setMaxActive(Integer.parseInt(env.getProperty("dataSource.maxActive", "10")));
		return dataSource;
	}
}
