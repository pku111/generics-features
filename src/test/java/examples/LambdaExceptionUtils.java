package examples;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class LambdaExceptionUtils {
    private LambdaExceptionUtils() {}

    @FunctionalInterface
    public interface ConsumerWithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<R, E extends Exception> {
        R get() throws E;
    }

    @FunctionalInterface
    public interface FunctionWithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface PredicateWithExceptions<T, E extends Exception> {
        boolean test(T t) throws E;
    }

    /**
     * .forEach(rethrowFromConsumer(name -> System.out.println(name))) or .forEach(rethrowFromConsumer(System.out::println))
     */
    public static <T, E extends Exception> Consumer<T> rethrowFromConsumer(ConsumerWithExceptions<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwActualException(exception);
            }
        };
    }

    /**
     * .map(rethrowFromSupplier(name -> NetworkInterface.getNetworkInterfaces())) or .map(rethrowFromSupplier(NetworkInterface::getNetworkInterfaces))
     */
    public static <R, E extends Exception> Supplier<R> rethrowFromSupplier(SupplierWithExceptions<R, E> supplier) throws E {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throwActualException(e);
                return null;
            }
        };
    }

    /**
     * .map(rethrowFromPredicate(name -> Class.forName(name))) or .map(rethrowFromPredicate(Class::forName))
     */
    public static <T, E extends Exception> Predicate<T> rethrowFromPredicate(PredicateWithExceptions<T, E> function) throws E  {
        return t -> {
            try {
                return function.test(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return false;
            }
        };
    }

    /**
     * .map(rethrowFromFunction(name -> Class.forName(name))) or .map(rethrowFromFunction(Class::forName))
     */
    public static <T, R, E extends Exception> Function<T, R> rethrowFromFunction(FunctionWithExceptions<T, R, E> function) throws E  {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }
}
