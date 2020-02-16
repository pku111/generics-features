package examples;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class _10_LambdaExceptionUtils {
    // ---------------------------------------------- issue ------------------------------------------------------------
    public void issue() {
        List<Integer> result = Stream.of(1, 2, 3)
//            .map(i -> toStringButCanFailHard(i)) // compilation failure
            .collect(toList());
    }

    String toStringButCanFailHard(int i) throws FatalException {
        throw new RuntimeException("Not yet implemented");
    }

    static class FatalException extends Exception {}

    // ---------------------------------------------- solution ---------------------------------------------------------

    static class WrapperRuntimeException extends RuntimeException {
        private Exception wrapped;

        public WrapperRuntimeException(Exception wrapped) {
            this.wrapped = wrapped;
        }
    }

    public void aSolution() throws Exception {
        try {
            List<String> result = Stream.of(1, 2, 3)
                .map(i -> {
                    try {
                        return toStringButCanFailHard(i);
                    } catch (FatalException e) {
                        throw new WrapperRuntimeException(e);
                    }})
                .collect(toList());
        } catch (WrapperRuntimeException e) {
            throw e.wrapped;
            //  exceptions can not be generic, we lose the exception type unless we use bespoke RuntimeExceptions
            //  for each checked exception
        }
    }

    public void solution() throws FatalException {
        List<String> result = Stream.of(1, 2, 3)
            .map(rethrowFromFunction(this::toStringButCanFailHard))
            .collect(toList());
    }

    // ---------------------------------------------- original ---------------------------------------------------------
    @FunctionalInterface
    interface FunctionWithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    static <T, R, E extends Exception> Function<T, R> rethrowFromFunction(FunctionWithExceptions<T, R, E> function) throws E {
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
    static <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }

    // ----------------------------------------------- simplified ------------------------------------------------------
    @FunctionalInterface
    interface StringFunctionWithExceptions<E extends Exception> {
        String apply(String t) throws E;
    }

    static <E extends Exception> Function<String, String> rethrowFromStringFunction(StringFunctionWithExceptions<E> function) throws E {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    // ----------------------------------------------- main-------------------------------------------------------------
    static void methodThatTakesAStringFunction(Function<String, String> function) {
        throw new RuntimeException("Not yet implemented");
    }

    static <T, R> void methodThatTakesAnyFunction(Function<T, R> function) {
        throw new RuntimeException("Not yet implemented");
    }

    public void main() {
        methodThatTakesAStringFunction(x -> "");
        methodThatTakesAStringFunction(x -> x);
        methodThatTakesAStringFunction(x -> null);
//        methodThatTakesAStringFunction(x -> 1); // compilation failure
//        methodThatTakesAStringFunction(x -> String.class); // compilation failure
//        methodThatTakesAStringFunction((Integer x) -> ""); // compilation failure

        methodThatTakesAnyFunction(x -> "");
        methodThatTakesAnyFunction(x -> x);
        methodThatTakesAnyFunction(x -> null);
        methodThatTakesAnyFunction(x -> 1);
        methodThatTakesAnyFunction(x -> String.class);
        methodThatTakesAnyFunction((Integer x) -> "");

        //noinspection Convert2MethodRef
        methodThatTakesAStringFunction(input -> function(input));
        methodThatTakesAStringFunction(this::function);

//        methodThatTakesAStringFunction(this::functionThatThrowsCheckedException); // compilation failure

        try {
            methodThatTakesAStringFunction(rethrowFromFunction(this::functionThatThrowsCheckedException));
        } catch (CheckedException e) {
            throw new RuntimeException("Not yet implemented");
        }

    }

    String function(String input) {
        throw new RuntimeException("Not yet implemented");
    }

    String functionThatThrowsCheckedException(String input) throws CheckedException {
        throw new CheckedException();
    }

    static class CheckedException extends Exception {}
}
