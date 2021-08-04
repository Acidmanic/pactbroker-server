/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author diego
 */
public class StringUtils {

    public static List<String> split(String string, String regEx) {

        return split(string, regEx, false);
    }

    public static List<String> split(String string, String regEx, boolean removeEmptyEntities) {

        String[] all = string.split(regEx, -1);

        ArrayList<String> segments = new ArrayList<>();

        for (String item : all) {

            if (!removeEmptyEntities || (item != null && item.length() > 0)) {

                segments.add(item);
            }
        }
        return segments;
    }

    public static List<String> extractPatterns(String rawInput, String regEx) {

        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(rawInput);

        boolean result = matcher.find();

        List<String> urls = new ArrayList<>();

        while (result) {

            String url = matcher.group();

            urls.add(url);

            result = matcher.find();
        }
        return urls;
    }

    public static boolean isNullOrEmpty(String title) {

        return title == null || title.length() == 0;
    }

    public static String stripSides(String string, String find) {

        if (isNullOrEmpty(string) || isNullOrEmpty(find)) {
            return string;
        }
        if (string.startsWith(find)) {
            string = string.substring(find.length(), string.length());
        }
        if (string.endsWith(find)) {
            string = string.substring(0, string.length() - find.length());
        }
        return string;
    }
}
