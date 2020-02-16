package examples;

public class _05_GenericClassExtendingGenericClassMoreSpecific {

    static class SuperClass<T, R> {
        R method(T t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class SomeClass<T> extends SuperClass<T, String> {
        @Override
        String method(T t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class SomeIntentionallyConfusingClass<T> extends SuperClass<String, T> {
        @Override
        T method(String t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class LockedInClass1 extends SuperClass<String, String> {
        @Override
        String method(String t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class LockedInClass2 extends SomeClass<String> {
        @Override
        String method(String t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class LockedInClass3 extends SomeIntentionallyConfusingClass<String> {
        @Override
        String method(String t) {
            throw new RuntimeException("Not yet implemented");
        }
    }

    public static void main(String[] args) {
        String result1 = new SuperClass<String, String>().method("");
        int result2 = new SuperClass<Integer, Integer>().method(1);

        String result3 = new SomeClass<String>().method("");
        String result4 = new SomeClass<Integer>().method(1);

        String result5 = new SomeIntentionallyConfusingClass<String>().method("");
        int result6 = new SomeIntentionallyConfusingClass<Integer>().method("");

        String result7 = new LockedInClass1().method("");
        String result8 = new LockedInClass2().method("");
        String result9 = new LockedInClass3().method("");
    }
}
