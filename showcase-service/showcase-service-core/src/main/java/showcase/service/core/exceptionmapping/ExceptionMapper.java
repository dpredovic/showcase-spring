package showcase.service.core.exceptionmapping;

public interface ExceptionMapper<T extends Throwable, R> {

    R map(T throwable, Class<? extends R> returnType);

    boolean canHandle(Throwable throwable);

    Class<R> baseReturnType();

    Class<T> baseExceptionType();

}
