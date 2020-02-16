package examples;

public class _04_Builders {
    interface Content {}
    interface Title extends Content {}

    interface Builder<T> {
        T build();
    }

//    interface ContentBuilder extends Builder<Content> {}
//    interface TitleBuilder extends ContentBuilder, Builder<Title> {} // compilation failure

//    interface ContentBuilder {}
//    interface TitleBuilder extends ContentBuilder, Builder<Title> {}

    static class ContentBuilder<T extends ContentBuilder<T>> { // not a builder!
        T withSharedField() {
            throw new RuntimeException("Not yet implemented");
        }
    }

    static class TitleBuilder extends ContentBuilder<TitleBuilder> implements Builder<Title> {
        TitleBuilder withField() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Title build() {
            throw new RuntimeException("Not yet implemented");
        }
    }

    public static void main(String[] args) {
        Title title = new TitleBuilder().withSharedField().withField().build();
    }
}
