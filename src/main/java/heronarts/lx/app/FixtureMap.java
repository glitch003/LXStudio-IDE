package heronarts.lx.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureMap {
    public static Map<String, List<Integer>> fixtures = new HashMap<String, List<Integer>>() {{
        put("topBorder", Arrays.asList(0));
        put("bottomBorder", Arrays.asList(1));
        put("stripAftPort", Arrays.asList(2, 3));
        put("stripForePort", Arrays.asList(4, 5));
        put("stripAftStarboard", Arrays.asList(6, 7));
        put("stripForeStarboard", Arrays.asList(8, 9));
    }};
    public static Map<String, List<String>> groups = new HashMap<String, List<String>>() {{
        put("borders", Arrays.asList("topBorder", "bottomBorder"));
        put("strips", Arrays.asList("stripAftPort", "stripForePort", "stripAftStarboard", "stripForeStarboard"));
    }};
    
}
