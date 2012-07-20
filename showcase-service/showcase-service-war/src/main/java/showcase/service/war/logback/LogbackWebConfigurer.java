package showcase.service.war.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.WebUtils;

import java.io.File;
import java.io.FileNotFoundException;
import javax.servlet.ServletContext;

public abstract class LogbackWebConfigurer {

    public static void init(ServletContext servletContext) {
        File configFile = getConfigFile(servletContext);

        if (configFile != null) {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();

            try {
                configurator.doConfigure(configFile);
            } catch (JoranException ignored) {
                StatusPrinter.printInCaseOfErrorsOrWarnings(context);
            }
        }
    }

    private static File getConfigFile(ServletContext servletContext) {
        String contextName = servletContext.getServletContextName();
        if (contextName == null) {
            contextName = WebUtils.extractFullFilenameFromUrlPath(servletContext.getContextPath());
        }
        String configFileName = contextName + "-logback.xml";
        File configFile = null;
        try {
            configFile = ResourceUtils.getFile("classpath:" + configFileName);
            servletContext.log("logback configuration file " + configFile + " found");
        } catch (FileNotFoundException e) {
            servletContext.log("logback configuration file " + configFileName + " not found");
            //
        }
        return configFile;
    }

}
