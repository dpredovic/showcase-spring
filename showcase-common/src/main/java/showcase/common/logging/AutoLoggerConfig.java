package showcase.common.logging;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

@Configuration
public class AutoLoggerConfig {

    @Bean
    public AutoLoggerBeanPostProcessor autoLoggerBeanPostProcessor() {
        return new AutoLoggerBeanPostProcessor();
    }

    public static class AutoLoggerBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
            ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
                        @Override
                        public void doWith(Field field) throws IllegalArgumentException {
                            ReflectionUtils.makeAccessible(field);
                            Class<?> fieldType = field.getType();
                            if (fieldType.equals(Logger.class)) {
                                ReflectionUtils.setField(field, bean, LoggerFactory.getLogger(bean.getClass()));
                            } else if (fieldType.equals(Log.class)) {
                                ReflectionUtils.setField(field, bean, LogFactory.getLog(bean.getClass()));
                            } else {
                                throw new IllegalArgumentException("Unknown @AutoLogger field type: " + fieldType.getCanonicalName());
                            }
                        }
                    }, new ReflectionUtils.FieldFilter() {
                        @Override
                        public boolean matches(Field field) {
                            return field.isAnnotationPresent(AutoLogger.class);
                        }
                    }
            );
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }
    }
}
