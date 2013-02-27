package showcase.service.core.exceptionmapping;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;

@Aspect
@Named
public class ExceptionMappingAspect {

	private Map<Class<? extends Throwable>, ExceptionMapper<? extends Throwable, ?>> exceptionMapperMap;
	@Inject
	private Collection<ExceptionMapper<? extends Throwable, ?>> exceptionMappers;

	@Pointcut("within(@ExceptionsMapped *)")
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

		do {
			ExceptionMapper<? extends Throwable, ?> exceptionMapper =
				getExceptionMapperDirectMatch(exceptionClass, returnType);
			if (exceptionMapper != null) {
				return exceptionMapper;
			}
			if (exceptionClass.equals(Throwable.class)) {
				return null;
			}
			exceptionClass = (Class<? extends Throwable>) exceptionClass.getSuperclass();
		} while (true);
	}

	private ExceptionMapper<? extends Throwable, ?> getExceptionMapperDirectMatch(Class<? extends Throwable> exceptionClass,
																				  Class<?> returnType) {
		ExceptionMapper<? extends Throwable, ?> exceptionMapper = exceptionMapperMap.get(exceptionClass);
		if (exceptionMapper.getReturnClass().isAssignableFrom(returnType)) {
			return exceptionMapper;
		}
		return null;
	}

	@PostConstruct
	private void init() {
		exceptionMapperMap = Maps.newHashMap();
		for (ExceptionMapper<? extends Throwable, ?> exceptionMapper : exceptionMappers) {
			exceptionMapperMap.put(exceptionMapper.getThrowableClass(), exceptionMapper);
		}
	}
}
