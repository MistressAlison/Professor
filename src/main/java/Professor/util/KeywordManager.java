package Professor.util;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeywordManager {
    public static HashMap<String, String> keywordMap = new HashMap<>();
    public static String ELEMENT;
    public static String CATALYST;
    public static String REACTANT;
    public static String SYNTHESIS;
    public static String VALENCY;
    public static String RECIPE;
    public static String CREATION;
    public static String SPECTRUMIZE;
    public static String NEUTRALIZER;
    public static String BRACED;

    public static String BOMB;
    public static String EXPLOSIVE_UNI;
    public static String NA;
    public static String ICE_BOMB;
    public static String LUFT;

    public static String getKeyword(String ID) {
        return keywordMap.getOrDefault(ID, "");
    }

    public static void setupKeyword(String ID, String key) {
        keywordMap.put(ID, key);
        Field[] fields = KeywordManager.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().toLowerCase().equals(ID)) {
                try {
                    f.set(null, key);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
