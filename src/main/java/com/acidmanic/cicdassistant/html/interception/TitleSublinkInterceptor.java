/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.interception;

import com.acidmanic.cicdassistant.html.A;
import com.acidmanic.cicdassistant.utility.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author diego
 */
public class TitleSublinkInterceptor implements HtmlInterceptor {

    @Override
    public void manipulate(Document document) {

        List<Element> headings = getAllHeadings(document);

        for (Element heading : headings) {

            String originalId = heading.attr("id");

            String anchorRef = StringUtils.isNullOrEmpty(originalId)
                    ? titleAsLink(heading.text())
                    : originalId;

            A anchor = new A("&nbsp;&#128279;", "#" + anchorRef);

            heading.attr("id", anchorRef);

            heading.append(anchor.toString());

        }
    }

    private List<Element> getAllHeadings(Document document) {

        ArrayList<Element> headings = new ArrayList<>();

        for (int i = 1; i < 6; i++) {

            String tag = "h" + i;

            Elements foundHeadings = document.select(tag);

            headings.addAll(foundHeadings);
        }

        return headings;

    }

    private String titleAsLink(String text) {

        char[] chars = text.toCharArray();

        StringBuilder sb = new StringBuilder();

        boolean lastEscaped = false;

        for (char c : chars) {

            if (Character.isAlphabetic(c)
                    || Character.isDigit(c)) {

                sb.append(Character.toLowerCase(c));

                lastEscaped = false;

            } else if (!lastEscaped) {

                sb.append("-");

                lastEscaped = true;
            }
        }

        return sb.toString();
    }

}
