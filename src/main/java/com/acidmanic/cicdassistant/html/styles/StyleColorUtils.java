/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.styles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author diego
 */
public class StyleColorUtils {

    public static StyleColor average(Collection<StyleColor> colors) {

        double red = 0, green = 0, blue = 0, alpha = 0;
        double count = 0;

        for (StyleColor color : colors) {

            red += color.getRed();
            green += color.getGreen();
            blue += color.getBlue();
            alpha += color.getAlpha();

            count += 1;
        }

        if (count == 0) {
            return new StyleColor();
        }
        red = red / count;
        green = green / count;
        blue = blue / count;
        alpha = alpha / count;

        return new StyleColor(red, green, blue, alpha);
    }

    public static StyleColor median(Collection<StyleColor> colors) {

        StyleColor average = average(colors);

        double minDistance = Double.POSITIVE_INFINITY;
        StyleColor median = new StyleColor();

        for (StyleColor color : colors) {

            double distance = euclideanDistance(color, average);

            if (distance < minDistance) {
                minDistance = distance;
                median = color;
            }
        }

        return median;
    }

    public static double euclideanDistance(StyleColor c1, StyleColor c2) {

        double r = Math.pow(c1.getRed(), 2.0) + Math.pow(c2.getRed(), 2);
        double g = Math.pow(c1.getGreen(), 2.0) + Math.pow(c2.getGreen(), 2);
        double b = Math.pow(c1.getBlue(), 2.0) + Math.pow(c2.getBlue(), 2);
        double a = Math.pow(c1.getAlpha(), 2.0) + Math.pow(c2.getAlpha(), 2);

        return Math.sqrt(r + g + b + a);

    }

    public static List<StyleColor> scaleOverAverage(Collection<StyleColor> colors) {

        List<StyleColor> normalizedColors = new ArrayList<>();

        StyleColor average = average(colors);

        colors.forEach(c -> normalizedColors.add(StyleColorUtils.scaleOverColor(c, average)));

        return normalizedColors;
    }

    public static List<StyleColor> scaleOverColor(Collection<StyleColor> normalizedColors, StyleColor tint) {

        List<StyleColor> scaledColors = new ArrayList<>();

        normalizedColors.forEach(c -> scaledColors.add(StyleColorUtils.scaleOverColor(c, tint)));

        return scaledColors;
    }

    public static List<StyleColor> scaleIntoFactor(Collection<StyleColor> colors, StyleColor average) {

        List<StyleColor> normalizedColors = new ArrayList<>();

        colors.forEach(c -> normalizedColors.add(scaleIntoFactor(c, average)));

        return normalizedColors;
    }

    private static StyleColor scaleOverColor(StyleColor c, StyleColor average) {

        double red = c.getRed();
        double green = c.getGreen();
        double blue = c.getBlue();
        double alpha = c.getAlpha();

        double r = average.getRed();
        double g = average.getGreen();
        double b = average.getBlue();
        double a = average.getAlpha();

        red = scaleOverValue(red, r);
        green = scaleOverValue(green, g);
        blue = scaleOverValue(blue, b);
        alpha = scaleOverValue(alpha, a);

        return new StyleColor(red, green, blue, alpha);
    }

    private static double scaleOverValue(double value, double average) {

        if (average == 0) {
            return 0;
        }
        return value / average;
    }

    public static StyleColor scaleIntoFactor(StyleColor normalizedColor, StyleColor tint) {

        double red = normalizedColor.getRed();
        double green = normalizedColor.getGreen();
        double blue = normalizedColor.getBlue();
        double alpha = normalizedColor.getAlpha();

        double r = tint.getRed();
        double g = tint.getGreen();
        double b = tint.getBlue();
        double a = tint.getAlpha();

        red = scaleIntoFactor(red, r);
        green = scaleIntoFactor(green, g);
        blue = scaleIntoFactor(blue, b);
        alpha = scaleIntoFactor(alpha, a);

        red = compensateValueIntoRange(red);
        green = compensateValueIntoRange(green);
        blue = compensateValueIntoRange(blue);
        alpha = compensateValueIntoRange(alpha);

        return new StyleColor(red, green, blue, alpha);

    }

    public static double sigmoid(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    public static double compensateValueIntoRange(double value) {

        if (value > 1 || value < 0) {

            value = sigmoid(value);
        }
        return value;
    }

    private static double scaleIntoFactor(double value, double factor) {

        return value * factor;
    }
}
