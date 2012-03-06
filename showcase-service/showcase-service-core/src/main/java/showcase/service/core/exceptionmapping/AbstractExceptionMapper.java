package showcase.service.core.exceptionmapping;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.core.GenericTypeResolver;
import showcase.common.logging.AutoLogger;

public abstract class AbstractExceptionMapper<T extends Throwable, R> implements ExceptionMapper<T, R> {

    private Class<R> returnClass;
    private Class<T> throwableClass;

    @AutoLogger
    private Logger logger;

    @Override
    public Class<R> getReturnClass() {
        return returnClass;
    }

    @Override
    public Class<T> getThrowableClass() {
        return throwableClass;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
        Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(getClass(), ExceptionMapper.class);
        if (classes == null) {
            throw new IllegalArgumentException("cannot find generic type info for " + getClass().getCanonicalName());
        }
        throwableClass = (Class<T>) classes[0];
        returnClass = (Class<R>) classes[1];
        logger.info("Exception mapper found for exception {}, returning {}", throwableClass, returnClass);
    }
}
