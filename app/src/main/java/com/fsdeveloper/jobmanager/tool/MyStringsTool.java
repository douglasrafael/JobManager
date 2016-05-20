package com.fsdeveloper.jobmanager.tool;

import android.widget.LinearLayout;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It features methods useful for treatment of strings.
 *
 * @author Created by Douglas Rafael on 15/05/2016.
 * @version 1.0
 */
public class MyStringsTool {
    private String[] REPLACES = {"a", "e", "i", "o", "u", "c"};
    private Pattern[] PATTERNS = null;

    /**
     * Creates the patterns with all the accentuation possibilities.
     */
    public void compilePatterns() {
        PATTERNS = new Pattern[REPLACES.length];
        PATTERNS[0] = Pattern.compile("[âãáàä]", Pattern.CASE_INSENSITIVE);
        PATTERNS[1] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
        PATTERNS[2] = Pattern.compile("[íìîï]", Pattern.CASE_INSENSITIVE);
        PATTERNS[3] = Pattern.compile("[óòôõö]", Pattern.CASE_INSENSITIVE);
        PATTERNS[4] = Pattern.compile("[úùûü]", Pattern.CASE_INSENSITIVE);
        PATTERNS[5] = Pattern.compile("[ç]", Pattern.CASE_INSENSITIVE);
    }

    /**
     * Remove accents string passed as parameter.
     *
     * @param text The string.
     * @return The string without accent.
     */
    public String removeAccents(String text) {
        String result = text;

        if (PATTERNS == null) {
            compilePatterns();
        }

        for (int i = 0; i < PATTERNS.length; i++) {
            Matcher matcher = PATTERNS[i].matcher(result);
            result = matcher.replaceAll(REPLACES[i]);
        }
        return result;
    }

    public static String join (List<String> s, String delimiter) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        Iterator<String> iterator = s.iterator();
        StringBuilder builder = new StringBuilder(iterator.next());
        while (iterator.hasNext()) {
            builder.append(delimiter).append(iterator.next());
        }
        return builder.toString();
    }

}