package showcase.service.core.exceptionmapping;

public interface ExceptionMapper<T extends Throwable, R> {

    R map(T throwable, Class<? extends R> returnType);

    Class<R> getReturnClass();

    Class<T> getThrowableClass();

}
