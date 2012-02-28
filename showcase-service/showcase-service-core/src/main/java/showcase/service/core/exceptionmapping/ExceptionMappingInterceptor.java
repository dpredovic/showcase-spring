package showcase.service.core.exceptionmapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class ExceptionMappingInterceptor<T extends Throwable, R> implements MethodInterceptor {

    private Map<Class<T>, ExceptionMapper<T, R>> exceptionHandlerMap;

    @Autowired
    private Collection<ExceptionMapper> exceptionMappers;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable throwable) {
            Class<?> returnType = invocation.getMethod().getReturnType();
            ExceptionMapper exceptionMapper = getExceptionMapper(throwable, returnType);
            if (exceptionMapper != null) {
                return exceptionMapper.map(throwable, returnType);
            }
            throw throwable;
        }
    }

    private ExceptionMapper<T, R> getExceptionMapper(Throwable exception, Class<?> returnType) {
        Class<?> exceptionClass = exception.getClass();
        ExceptionMapper<T, R> handler = exceptionHandlerMap.get(exceptionClass);
        while (handler == null && !exceptionClass.equals(Throwable.class)) {
            exceptionClass = exceptionClass.getSuperclass();
            handler = exceptionHandlerMap.get(exceptionClass);
        }
        return handler;
    }

    private ExceptionMapper<T, R> getExceptionMapper(Class<?> exceptionClass, Class<?> returnType) {
        ExceptionMapper<T, R> handler = exceptionHandlerMap.get(exceptionClass);
        if (handler.baseReturnType().isAssignableFrom(returnType)) {
            return handler;
        }
        return null;
    }

    @PostConstruct
    private void init() {
        exceptionHandlerMap = new HashMap<Class<T>, ExceptionMapper<T, R>>();
        for (ExceptionMapper exceptionMapper : exceptionMappers) {
            exceptionHandlerMap.put(exceptionMapper.baseExceptionType(), exceptionMapper);
        }
    }

}
