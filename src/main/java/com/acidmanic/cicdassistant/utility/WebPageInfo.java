/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.util.List;

/**
 *
 * @author diego
 */
public class WebPageInfo {

    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void load(String address) {

        String htmlContent = new HttpGetPageClient().get(address);

        this.title = getTitle(htmlContent, address);

        this.description = getDescription(htmlContent, address);
    }

    private String getDescription(String pageContent, String defaultDescription) {

        List<String> metaData = StringUtils.extractPatterns(pageContent, "<meta.+name=.+content=.+>");

        String author = "";

        String description = "";

        for (String metaHtml : metaData) {

            String m = metaHtml.trim();

            String name = readAttribute("name", m);

            String content = readAttribute("content", m);

            if (name.length() > 0 && content.length() > 0) {

                if ("author".equalsIgnoreCase(name)) {

                    author = content + ": ";
                }
                if ("description".equalsIgnoreCase(name)) {

                    description = content;
                }
            }
        }

        if (description.length() == 0) {

            description = defaultDescription;
        }

        return author + description;
    }

    private String getTitle(String pageHtmlContent, String defaultTitle) {

        if (pageHtmlContent == null || pageHtmlContent.length() == 0) {
            return defaultTitle;
        }

        String title = getTagContent(pageHtmlContent, "title");

        if (title == null || title.length() == 0) {
            title = getTagContent(pageHtmlContent, "h1");
        }

        if (title == null || title.length() == 0) {
            title = defaultTitle;
        }
        return title;
    }

    private String getTagContent(String pageHtmlContent, String tagName) {

        if (pageHtmlContent == null || pageHtmlContent.length() == 0) {
            return "";
        }

        String lowerCaseContent = pageHtmlContent.toLowerCase();

        int st = lowerCaseContent.indexOf("<" + tagName.toLowerCase());

        if (st > -1) {

            st = lowerCaseContent.indexOf(">", st);

            if (st > -1) {

                st += 1;

                int nd = lowerCaseContent.indexOf("</", st);

                if (nd > -1) {

                    if (st < nd) {

                        String titleContent = pageHtmlContent.substring(st, nd);

                        if (!titleContent.contains("<") && !titleContent.contains(">")) {

                            return titleContent.trim();
                        }
                    }

                }
            }
        }
        return "";
    }

    private String readAttribute(String name, String tagHeader) {

        String[] headerSegments = tagHeader.split("\\s", -1);

        if (headerSegments != null) {

            for (int i = 0; i < headerSegments.length - 2; i++) {

                String segment = headerSegments[i].trim();

                if (segment.equalsIgnoreCase(name)) {

                    if ("=".equals(headerSegments[i + 1].trim())) {

                        return headerSegments[i + 2].trim();
                    }
                }
            }
        }
        return "";
    }
}
