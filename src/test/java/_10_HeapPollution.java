import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class _10_HeapPollution {
    private static class ArrayBuilder {
//        @SafeVarargs
        public static <T> void addToList (List<T> listArg, T... elements) {
            listArg.addAll(Arrays.asList(elements));
        }

        public static void faultyMethod(List<String>... l) {
            Object[] objectArray = l;     // Valid
            objectArray[0] = singletonList(42);
            String s = l[0].get(0);       // ClassCastException thrown here
        }
    }

    @Test
    void example() {
        List<String> stringListA = new ArrayList<>();
        List<String> stringListB = new ArrayList<>();

        ArrayBuilder.addToList(stringListA, "Seven", "Eight", "Nine");
        ArrayBuilder.addToList(stringListB, "Ten", "Eleven", "Twelve");

        List<List<String>> listOfStringLists = new ArrayList<>();

        ArrayBuilder.addToList(listOfStringLists, stringListA, stringListB);
        System.out.println(listOfStringLists);

        ArrayBuilder.faultyMethod(singletonList("Hello!"), singletonList("World!"));
    }
}
