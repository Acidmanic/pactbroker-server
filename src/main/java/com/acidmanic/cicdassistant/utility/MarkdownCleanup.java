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
public class MarkdownCleanup {
    
    public String clean(String markdown) {

        if (StringUtils.isNullOrEmpty(markdown)) {
            return "\n\n_Empty Document_\n\n";
        }

        String[] lines = markdown.split("\n", -1);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {

            if (isTableHeaderLine(lines, i)) {

                if (!emptyNewLine(lines, i - 2)) {
                    sb.append("\n");
                }
                if (!emptyNewLine(lines, i - 1)) {
                    sb.append("\n");
                }
            }
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }

    private boolean isTableHeaderLine(String[] lines, int i) {

        if (i + 1 >= lines.length) {
            return false;
        }

        String currentLine = lines[i].trim();
        String nextLine = lines[i + 1].trim();

        if (!currentLine.startsWith("|") || !nextLine.startsWith("|")) {

            return false;
        }
        int currentPipes = count(lines[i], '|');
        int nextPipes = count(lines[i + 1], '|');

        if (currentPipes < 2 || nextPipes < 2 || currentPipes != nextPipes) {
            return false;
        }
        nextLine = nextLine.replaceAll("\\-", "").trim();
        nextLine = nextLine.replaceAll(":", "");
        if (nextLine.length() != nextPipes) {
            return false;
        }
        nextLine = nextLine.replaceAll("\\|", "");
        if (!nextLine.isEmpty()) {
            return false;
        }
        return true;
    }

    private int count(String line, char c) {

        int count = 0;

        char[] chars = line.toCharArray();

        for (char ch : chars) {

            if (ch == c) {
                count += 1;
            }
        }
        return count;
    }

    private boolean emptyNewLine(String[] lines, int i) {

        if (i < 0 || i >= lines.length) {
            return false;
        }
        return lines[i].trim().length() == 0;
    }
}
