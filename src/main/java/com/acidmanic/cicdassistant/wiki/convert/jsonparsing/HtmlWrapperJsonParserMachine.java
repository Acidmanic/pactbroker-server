/*
 * The MIT License
 *
 * Copyright 2019 Mani Moayedi (acidmanic.moayedi@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.acidmanic.wiki.convert.jsonparsing;

import com.acidmanic.wiki.convert.jsonparsing.states.InObjectSymbolsState;
import com.acidmanic.wiki.convert.jsonparsing.states.InRawValueState;
import com.acidmanic.wiki.convert.jsonparsing.states.InStrings;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class HtmlWrapperJsonParserMachine {

    private static final String START_OPEN = "<span class=\"";
    private static final String START_CLOSE = "\">";
    private static final String END_TAG = "</span>";

    private boolean colulnSeen = false;

    private String rawValueClass = "json-in-value";
    private String stringClass = "json-in-string";
    private String keyClass = "json-in-keystring";

    public String getRawValueClass() {
        return rawValueClass;
    }

    public void setRawValueClass(String rawValueClass) {
        this.rawValueClass = rawValueClass;
    }

    public String getStringClass() {
        return stringClass;
    }

    public void setStringClass(String stringClass) {
        this.stringClass = stringClass;
    }

    private String getRawValueStartTag() {
        return START_OPEN + this.rawValueClass + START_CLOSE;
    }

    private String getStringStartTag() {
        return START_OPEN
                + (this.colulnSeen ? this.stringClass : this.keyClass)
                + START_CLOSE;
    }

    public String getKeyClass() {
        return keyClass;
    }

    public void setKeyClass(String keyClass) {
        this.keyClass = keyClass;
    }

    public String parse(String json) {
        char[] chars = json.toCharArray();

        JsonStringState state = new InObjectSymbolsState();

        StringBuilder sb = new StringBuilder();

        for (char c : chars) {

            if (c == '{' || c == ',') {
                colulnSeen = false;
            } else if (c == ':') {
                colulnSeen = true;
            }

            JsonStringState res = state.whatsNext(c);
            if (res.getClass() != state.getClass()) {
                onStateUpdate(c, sb, state, res);
                state = res;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void onStateUpdate(char onChar, StringBuilder sb, JsonStringState previouse, JsonStringState next) {

        String stringTagStart = getStringStartTag();
        String valuetagStart = getRawValueStartTag();

        if (next.getClass() == InStrings.class) {
            sb.append(stringTagStart);
            sb.append(onChar);
        } else if (previouse.getClass() == InStrings.class) {
            sb.append(onChar).append(END_TAG);
        } else if (next.getClass() == InRawValueState.class) {
            sb.append(valuetagStart).append(onChar);
        } else if (previouse.getClass() == InRawValueState.class) {
            sb.append(END_TAG).append(onChar);
        } else {
            sb.append(onChar);
        }
    }
}
