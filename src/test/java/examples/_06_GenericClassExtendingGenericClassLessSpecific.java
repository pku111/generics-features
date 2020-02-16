package examples;

public class _06_GenericClassExtendingGenericClassLessSpecific {
    static class SuperClass<T> {
        T method(String string) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class SomeClass<T, K> extends SuperClass<T> {
//        @Override // compilation failure
        T method(K input) {
            throw new RuntimeException("Not yet implemented");
        }

        T someOtherMethod(K input) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    public static void main(String[] args) {
        String result1 = new SuperClass<String>().method("");
        String result2 = new SomeClass<String, Integer>().method("");
        String result3 = new SomeClass<String, Integer>().method(1);
//        String result4 = new SomeClass<String, String>().method(""); // compilation failure

        String result5 = new SomeClass<String, Integer>().someOtherMethod(1);
//        String result6 = new SomeClass<String, Integer>().someOtherMethod(""); // compilation failure
        String result7 = new SomeClass<String, String>().someOtherMethod("");

    }
}
