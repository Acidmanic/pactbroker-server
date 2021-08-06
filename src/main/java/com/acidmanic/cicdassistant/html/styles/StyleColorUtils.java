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

    public static List<StyleColor> parse(Collection<String> codes) {

        List<StyleColor> colors = new ArrayList<>();

        for (String code : codes) {

            StyleColor color = StyleColor.fromCode(code);

            colors.add(color);
        }

        return colors;
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

    public static StyleColor saturateAtMax(StyleColor color) {

        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();

        double[] rgb = normalize(red, green, blue);

        return new StyleColor(rgb[0], rgb[1], rgb[2], 1.0);
    }

    public static StyleColor saturateMore(StyleColor color, double percent) {

        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();

        double min = min(red, green, blue);
        double max = max(red, green, blue);

        if (min == max) {
            return color;
        }

        double range = max - min;

        double denuminator = percent * (range - 1) + 1;

        return new StyleColor(
                (red - min) / denuminator,
                (green - min) / denuminator,
                (blue - min) / denuminator, 1.0);
    }

    private static double min(double... values) {

        double min = Double.POSITIVE_INFINITY;

        for (double v : values) {

            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    private static double max(double... values) {

        double max = Double.NEGATIVE_INFINITY;

        for (double v : values) {

            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private static double[] normalize(double... values) {

        double[] result = new double[values.length];

        double min = min(values);
        double max = max(values);

        if (min == max) {
            return result;
        }

        double range = max - min;

        for (int i = 0; i < values.length; i++) {

            result[i] = (values[i] - min) / range;
        }
        return result;
    }

    private static double average(double... values) {

        if (values.length == 0) {

            return 0;
        }
        double sum = 0;
        double count = 0;

        for (double value : values) {

            sum += value;
            count += 1;
        }
        return sum / count;
    }

    private static double variance(double... values) {

        double average = average(values);

        double sumOfPowers = 0;

        for (double value : values) {

            sumOfPowers += Math.pow(value - average, 2);
        }
        return Math.sqrt(sumOfPowers);
    }

    private static double range(double... values) {

        double min = min(values);
        double max = max(values);
        double range = max - min;

        return range;
    }

    public static StyleColor mostSaturate(List<StyleColor> colors) {

        double maximumVariance = 0;

        StyleColor mostSaturate = new StyleColor();

        for (StyleColor color : colors) {

            double variance = range(color.getRed(), color.getGreen(), color.getBlue());

            if (variance > maximumVariance) {

                maximumVariance = variance;

                mostSaturate = color;
            }
        }

        System.out.println("Most saturate color: "
                + mostSaturate.toCode()
                + "with variance: " + maximumVariance);

        return mostSaturate;
    }

}
