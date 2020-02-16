package examples;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class _01_TypeArgumentsAtRuntime {
    private static class SuperT1 {}
    private static class SubT1_1 extends SuperT1 {}
    private static class SubT1_2 extends SuperT1  {}

    private static class SuperT2 {}
    private static class SubT2_1 extends SuperT2 {}
    private static class SubT2_2 extends SuperT2 {}

    private static class SuperClass<T1 extends SuperT1, T2 extends SuperT2, T3, T4> {
//        private SuperClass() {
//            List<Class<?>> typeArguments = ReflectionUtils.getTypeArguments(SuperClass.class, getClass());
//            System.out.println(typeArguments);
//        }
    }

    private static class Actual1 extends SuperClass<SubT1_1, SubT2_1, String, List<String>> {}
    private static class Actual2 extends SuperClass<SubT1_2, SubT2_2, Integer, Set<Boolean>> {}

    @Test
    void typeArguments() {
        SuperClass<SubT1_1, SubT2_1, String, List<String>> instanceByGenericType1 = new SuperClass<>();
        SuperClass<SubT1_1, SubT2_1, String, List<String>> instanceOdActual1 = new Actual1();

        SuperClass<SubT1_2, SubT2_2, Integer, Set<Boolean>> instanceByGenericType2 = new SuperClass<>();
        SuperClass<SubT1_2, SubT2_2, Integer, Set<Boolean>> instanceOdActual2 = new Actual2();

        List<Class<?>> typeArguments1 = ReflectionUtils.getTypeArguments(SuperClass.class, Actual1.class);
        System.out.println(typeArguments1);

        List<Class<?>> typeArguments2 = ReflectionUtils.getTypeArguments(SuperClass.class, Actual1.class);
        System.out.println(typeArguments2);
    }
}
