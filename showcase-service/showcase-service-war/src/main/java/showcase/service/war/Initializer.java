package showcase.service.war;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import showcase.service.war.logback.LogbackContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Initializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WarConfig.class);

		servletContext.addListener(new LogbackContextListener());
		servletContext.addListener(new ContextLoaderListener(applicationContext));

		ServletRegistration.Dynamic cxf = servletContext.addServlet("cxf", new CXFServlet());
		cxf.setLoadOnStartup(1);
		cxf.addMapping("/*");
	}

}
