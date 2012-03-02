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

    private Map<Class<? extends Throwable>, ExceptionMapper<? extends Throwable, ?>> exceptionMapperMap;

    @Autowired
    private Collection<ExceptionMapper<? extends Throwable, ?>> exceptionMappers;

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    protected void annotatedAsService() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Around("annotatedAsService()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            Class<?> returnType = ((MethodSignature) pjp.getStaticPart().getSignature()).getReturnType();
            ExceptionMapper exceptionMapper = getExceptionMapper(throwable, returnType);
            if (exceptionMapper != null) {
                return exceptionMapper.map(throwable, returnType);
            }
            throw throwable;
        }
    }

    @SuppressWarnings("unchecked")
    private ExceptionMapper<? extends Throwable, ?> getExceptionMapper(Throwable exception, Class<?> returnType) {
        Class<? extends Throwable> exceptionClass = exception.getClass();

        ExceptionMapper<? extends Throwable, ?> exceptionMapper;
        while (!exceptionClass.equals(Throwable.class)) {
            exceptionMapper = getExceptionMapperDirectMatch(exceptionClass, returnType);
            if (exceptionMapper != null) {
                return exceptionMapper;
            }
            exceptionClass = (Class<? extends Throwable>) exceptionClass.getSuperclass();
        }
        return null;
    }

    private ExceptionMapper<? extends Throwable, ?> getExceptionMapperDirectMatch(Class<? extends Throwable> exceptionClass, Class<?> returnType) {
        ExceptionMapper<? extends Throwable, ?> exceptionMapper = exceptionMapperMap.get(exceptionClass);
        if (exceptionMapper.getReturnClass().isAssignableFrom(returnType)) {
            return exceptionMapper;
        }
        return null;
    }

    @PostConstruct
    private void init() {
        exceptionMapperMap = new HashMap<Class<? extends Throwable>, ExceptionMapper<? extends Throwable, ?>>();
        for (ExceptionMapper<? extends Throwable, ?> exceptionMapper : exceptionMappers) {
            exceptionMapperMap.put(exceptionMapper.getThrowableClass(), exceptionMapper);
        }
    }

}
