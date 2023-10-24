import java.util.List;

public class GeneratorJson {
    String json;

    public GeneratorJson(List<String> names, List<Object> values) {
        json = names.get(0);
        json = "{";
        for (int i = 0; i < names.size(); i++) {
            json += "\n\"" + names.get(i) + "\": " + values.get(i).toString();
            if (i < names.size() - 1) {
                json += ",";
            }
        }
        json += "\n}";
    }

    public String getJson() {
        return json;
    }
}
