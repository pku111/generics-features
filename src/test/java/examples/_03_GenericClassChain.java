package examples;

public class _03_GenericClassChain {
    public static void main(String[] args) {
        // no genrics
        new SubClass().doSomethingSpecific().chain();
//        new SubClass().chain().doSomethingSpecific();

        // with generics
        new GenericSuperClass<>().chain().chain().chain();

        new SubClass1().chain().chain().doSomethingSpecific().chain().finish();
        new SubClass2().chain().chain().chain().finish();
    }

    // no generics
    @SuppressWarnings("UnusedReturnValue")
    private static class SuperClass {
        SuperClass chain() {
            System.out.println("chain");
            return this;
        }
    }

    private static class SubClass extends SuperClass {
        SubClass doSomethingSpecific() {
            System.out.println("doSomethingSpecific");
            return this;
        }
    }

    // with generics
    @SuppressWarnings("unchecked")
    private static class GenericSuperClass<T extends GenericSuperClass<T>> {
        T chain() {
            System.out.println("chain");
            return (T) this;
        }
    }

    private static class SubClass1 extends GenericSuperClass<SubClass1> {
        SubClass1 doSomethingSpecific() {
            System.out.println("doSomethingSpecific");
            return this;
        }

        void finish() {
            System.out.println("finish1");
        }
    }

    private static class SubClass2 extends GenericSuperClass<SubClass2> {
        @Override
        SubClass2 chain() {
            System.out.println("chain2");
            return this;
        }

        void finish() {
            System.out.println("finish2");
        }
    }
}
