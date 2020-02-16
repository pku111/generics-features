package examples;

@SuppressWarnings("ALL")
public class _02_GenericTypePropagation {
    public static void main(String[] args) {
        assertThat(string("")).isEmpty();
        assertThat(integer(1)).isZero();

        assertThat(string("")).isEqualTo("");
        assertThat(integer(1)).isEqualTo(1);
    }

    private static <ASSERTION extends Assertion> ASSERTION assertThat(Request<ASSERTION> input) {
        throw new RuntimeException("Not yet implemented");
    }

    public interface Request<ASSERTION extends Assertion> { }

    static Request<StringAssertion> string(String string) {
        throw new RuntimeException("Not yet implemented");
    }

    static Request<IntegerAssertion> integer(int integer) {
        throw new RuntimeException("Not yet implemented");
    }

    private interface Assertion {}
    private static class StringAssertion implements Assertion {
        public boolean isEmpty() {
            return true;
        }
        public boolean isEqualTo(String string) {
            throw new RuntimeException("Not yet implemented");
        }
    }
    private static class IntegerAssertion implements Assertion {
        public boolean isZero() {
            return true;
        }
        public boolean isEqualTo(int integer) {
            throw new RuntimeException("Not yet implemented");
        }
    }
}
