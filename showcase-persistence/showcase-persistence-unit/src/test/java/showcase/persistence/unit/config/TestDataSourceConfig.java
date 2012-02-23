package showcase.persistence.unit.config;

import javax.sql.DataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
//@Profile("junit")
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
        formatter.setLoggingType(LoggingType.MULTI_LINE);
        formatter.setSqlPrefix("SQL:\r");

        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("init.sql").build();

        Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(embeddedDatabase);
        dataSource.setLogFormatter(formatter);
        return dataSource;
    }

}
