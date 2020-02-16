package examples;

public class _07_LambdaInjection {
    @FunctionalInterface
    interface Strategy<T, R> {
        R execute(T t);
    }

    static class GenericStrategyUser<T, R, K> {
        private Strategy<T, R> strategy;

        public GenericStrategyUser(Strategy<T, R> strategy) {
            this.strategy = strategy;
        }
    }

    static class ComplexStringStrategy implements Strategy<String, String> {
        @Override
        public String execute(String s) {
            throw new RuntimeException("Not yet implemented: Execute complex logic");
        }
    }

    public static void main(String[] args) {
        GenericStrategyUser<String, String, ?> user1 = new GenericStrategyUser<>(new ComplexStringStrategy());
        GenericStrategyUser<String, String, String> user2 = new GenericStrategyUser<>(new ComplexStringStrategy());
//        GenericStrategyUser<String, Integer, String> user3 =
//            new GenericStrategyUser<>(new ComplexStringStrategy()); // compilation failure

        GenericStrategyUser<String, Integer, ?> user4 = new GenericStrategyUser<>(new Strategy<String, Integer>() {
            @Override
            public Integer execute(String s) {
                throw new RuntimeException("Not yet implemented");
            }
        });

        GenericStrategyUser<String, String, ?> user5 = new GenericStrategyUser<>(new Strategy<String, String>() {
            @Override
            public String execute(String t) {
                return t;
            }
        });

        GenericStrategyUser<String, String, ?> user6 = new GenericStrategyUser<>(t -> t);

//        GenericStrategyUser<String, String, ?> user7 =
//            new GenericStrategyUser<String, String, String>(Function.<String>identity()); // compilation failure

        GenericStrategyUser<String, String, ?> user8 = new GenericStrategyUser<>(nopStrategy());
    }

    private static Strategy<String, String> nopStrategy() {
        return t -> t;
    }
}
