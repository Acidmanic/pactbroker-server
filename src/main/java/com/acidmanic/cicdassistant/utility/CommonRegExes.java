/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

/**
 *
 * @author diego
 */
public class CommonRegExes {

    public static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String SHA1_REGEX = "\\b[0-9a-f]{5,40}\\b";
    public static final String EMAIL_REGEX = "[0-9a-zA-z\\._]+@[0-9a-zA-z\\.:_]+[0-9a-zA-z]+";

}
