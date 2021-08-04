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
public abstract class JsonInterceptorBase {

    public void parse(String json) {

        char[] chars = json.toCharArray();

        JsonStringState state = new InObjectSymbolsState();

        StringBuilder sb = new StringBuilder();

        for (char c : chars) {

            JsonStringState newState = state.whatsNext(c);

            if (newState.getClass() != state.getClass()) {

                if (stateKeepsChars(state.getClass())) {

                    sb.append(c);

                    preDeliver(sb.toString(), state);

                    sb = new StringBuilder();

                } else {

                    preDeliver(sb.toString(), state);

                    sb = new StringBuilder();

                    sb.append(c);
                }

                state = newState;
            } else {
                /**
                 * TODO: The correct solution to detect white spaces, would be
                 * having a InWhiteSpaceState class be used in stateMachine. For
                 * now the following code is to trim white spaces and call
                 * deliver method per each symbol character. This way, the class
                 * can also be used for prettifying purposes.
                 *
                 */
                if (isSymbol(state)) {

                    if (!Character.isWhitespace(c)) {

                        sb.append(c);

                        preDeliver(sb.toString(), state);

                        sb = new StringBuilder();
                    }
                } else {
                    sb.append(c);
                }
            }

        }
        sb.append("\n");

        preDeliver(sb.toString(), state);

    }

    protected void preDeliver(String text, JsonStringState state) {

        text = text.trim();

        deliver(text, state);
    }

    protected abstract void deliver(String text, JsonStringState state);

    private boolean stateKeepsChars(Class type) {
        return type.getName().equals(InStrings.class.getName());
    }

    protected boolean isSymbol(JsonStringState state) {
        return InObjectSymbolsState.class.getName()
                .equals(state.getClass().getName());
    }

    protected boolean isString(JsonStringState state) {
        return InStrings.class.getName()
                .equals(state.getClass().getName());
    }

    protected boolean isRaw(JsonStringState state) {
        return InRawValueState.class.getName()
                .equals(state.getClass().getName());
    }
}
