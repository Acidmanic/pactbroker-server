/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.styles;

/**
 *
 * @author diego
 */
public class StyleColor {

    private double red;
    private double green;
    private double blue;
    private double alpha = 1;

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public StyleColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1;
    }

    public StyleColor(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public StyleColor() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.alpha = 0;
    }

    private String fixHexByte(int value) {

        String code = Integer.toHexString(value);

        if (code.length() == 1) {
            code = "0" + code;
        }
        return code;
    }

    public String toCode() {
        int r = (int) (this.red * 255);
        int g = (int) (this.green * 255);
        int b = (int) (this.blue * 255);
        int a = (int) (this.alpha * 255);

        String code = "#"
                + fixHexByte(r)
                + fixHexByte(g)
                + fixHexByte(b);

        if (a != 255) {
            code += fixHexByte(a);
        }
        return code;
    }

    public static StyleColor fromCode(String code) {

        if (code == null) {

            return new StyleColor();
        }

        if (code.startsWith("#")) {

            code = code.substring(1, code.length());
        }

        String sR, sG, sB, sA;

        switch (code.length()) {
            case 3:
                sR = code.substring(0, 1);
                sR += sR;
                sG = code.substring(1, 2);
                sG += sG;
                sB = code.substring(2, 3);
                sB += sB;
                sA = "FF";
                break;
            case 4:
                sR = code.substring(0, 1);
                sR += sR;
                sG = code.substring(1, 2);
                sG += sG;
                sB = code.substring(2, 3);
                sB += sB;
                sA = code.substring(3, 4);
                sA += sA;
                break;
            case 6:
                sR = code.substring(0, 2);
                sG = code.substring(2, 4);
                sB = code.substring(4, 6);
                sA = "FF";
                break;
            case 8:
                sR = code.substring(0, 2);
                sG = code.substring(2, 4);
                sB = code.substring(4, 6);
                sA = code.substring(6, 8);
                break;
            default:
                return new StyleColor();
        }

        try {
            double red = (Integer.parseInt(sR, 16) / 255.0);
            double green = (Integer.parseInt(sG, 16) / 255.0);
            double blue = (Integer.parseInt(sB, 16) / 255.0);
            double alpha = (Integer.parseInt(sA, 16) / 255.0);

            return new StyleColor(red, green, blue, alpha);

        } catch (Exception e) {
        }
        return new StyleColor();
    }

}
