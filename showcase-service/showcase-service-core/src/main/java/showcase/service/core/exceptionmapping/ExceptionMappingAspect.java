package showcase.service.core.exceptionmapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionMappingAspect {

    private Map<Class<? extends Throwable>, ExceptionMapper<? extends Throwable, ?>> exceptionHandlerMap;

    @Autowired
    private Collection<ExceptionMapper<? extends Throwable, ?>> exceptionMappers;

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    protected void annotatedAsService() {
    }

    @Around("annotatedAsService()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            MethodSignature signature = (MethodSignature) pjp.getStaticPart().getSignature();
            Class<?> returnType = signature.getReturnType();
            ExceptionMapper exceptionMapper = getExceptionMapper(throwable, returnType);
            if (exceptionMapper != null) {
                return exceptionMapper.map(throwable, returnType);
            }
            throw throwable;
        }
    }

    private ExceptionMapper<? extends Throwable, ?> getExceptionMapper(Throwable exception, Class<?> returnType) {
        Class<? extends Throwable> exceptionClass = exception.getClass();
        ExceptionMapper<? extends Throwable, ?> handler = getExceptionMapper(exceptionClass, returnType);
        while (handler == null && !exceptionClass.equals(Throwable.class)) {
            exceptionClass = (Class<? extends Throwable>) exceptionClass.getSuperclass();
            handler = getExceptionMapper(exceptionClass, returnType);
        }
        return handler;
    }

    private ExceptionMapper<? extends Throwable, ?> getExceptionMapper(Class<? extends Throwable> exceptionClass, Class<?> returnType) {
        ExceptionMapper<? extends Throwable, ?> handler = exceptionHandlerMap.get(exceptionClass);
        if (handler.baseReturnType().isAssignableFrom(returnType)) {
            return handler;
        }
        return null;
    }

    @PostConstruct
    private void init() {
        exceptionHandlerMap = new HashMap<Class<? extends Throwable>, ExceptionMapper<? extends Throwable, ?>>();
        for (ExceptionMapper<? extends Throwable, ?> exceptionMapper : exceptionMappers) {
            exceptionHandlerMap.put(exceptionMapper.baseExceptionType(), exceptionMapper);
        }
    }

}
