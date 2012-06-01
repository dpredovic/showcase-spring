package showcase.service.core.exceptionmapping;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;

@Slf4j
public abstract class AbstractExceptionMapper<T extends Throwable, R> implements ExceptionMapper<T, R> {

    private Class<R> returnClass;
    private Class<T> throwableClass;

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
        log.info("Exception mapper found for exception {}, returning {}", throwableClass, returnClass);
    }
}
