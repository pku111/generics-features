package examples;

public class _09_WhenNotToUseGenericsWithGenerics {
    static class Resource<T> {
        private GeneralClass<T> generalClass;

        Resource(GeneralClass<T> generalClass) {
            this.generalClass = generalClass;
        }

        String call(String string) {
            return generalClass.call(string);
        }
    }

    static class GeneralClass<T> {
        private SpecificClass<T> specificClass;

        GeneralClass(SpecificClass<T> specificClass) {
            this.specificClass = specificClass;
        }

        public String call(String string) {
            return specificClass.call(string);
        }
    }

    static abstract class SpecificClass<T> {
        public String call(String string) {
            return "SpecificClass returning: " + string;
        }
    }

    interface Live {}
    interface Vod {}

    static class LiveClass extends SpecificClass<Live> {
        @Override
        public String call(String string) {
            return "LiveClass returning: " + string;
        }
    }
    static class VodClass extends SpecificClass<Vod> {
        @Override
        public String call(String string) {
            return "VodClass returning: " + string;
        }
    }

    public static void main(String[] args) {
        String liveResponse = new Resource<>(new GeneralClass<>(new LiveClass())).call("input");
        System.out.println(liveResponse);
        String vodResponse = new Resource<>(new GeneralClass<>(new VodClass())).call("input");
        System.out.println(vodResponse);

        Resource<Live> resource = new Resource<>(new GeneralClass<>(new LiveClass()));
//        resource = new Resource<>(new GeneralClass<>(new VodClass())); // compilation failure
    }
}
