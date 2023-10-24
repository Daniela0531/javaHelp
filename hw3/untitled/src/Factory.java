import javax.annotation.processing.Generated;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Factory {
    private static final CachedCompiler JCC = CompilerUtils.DEBUGGING ?
            new CachedCompiler(new File(parent, "src/test/java"), new File(parent, "target/compiled")) :
            CompilerUtils.CACHED_COMPILER;

    //    TODO
//    <T extends Objects> GeneratorJson toJson(Class className) {
    GeneratorJson toJson(Person className) throws IllegalAccessException, InstantiationException {
//        Person person;
        String className1 = "mypackage.MyClass";
        String javaCode = "package mypackage;\n" +
                "public class MyClass implements Runnable {\n" +
                "    public void run() {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}\n";
        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className1, javaCode);
        Runnable runner = (Runnable) aClass.newInstance();
        runner.run();
        List<Field> fields = Arrays.asList(Person.class.getDeclaredFields());
        List<String> names = new ArrayList<>(fields.size());
        List<Object> values = new ArrayList<>(fields.size());
        if (!names.isEmpty()) {
            for (Field field : fields) {
                names.add(field.getName());
                values.add(field.get(names.get(names.size() - 1)));
            }
        }
        return new GeneratorJson(names, values);
    }
}
