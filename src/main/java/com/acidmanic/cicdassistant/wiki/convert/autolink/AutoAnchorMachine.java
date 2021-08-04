/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import com.acidmanic.wiki.convert.autolink.Anchor;
import com.acidmanic.wiki.convert.autolink.AnchorSource;
import com.acidmanic.wiki.convert.autolink.Mark;
import com.acidmanic.wiki.convert.autolink.SearchAndReplaceText;
import com.acidmanic.wiki.convert.autolink.SearchTarget;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class AutoAnchorMachine {

    private final HashMap<SearchTarget, String> allReplacements = new HashMap<>();
    private final List<SearchTarget> sortedKeys;

    public AutoAnchorMachine(AnchorSource... providers) {
        this(Arrays.asList(providers));
    }

    public AutoAnchorMachine(Collection<AnchorSource> providers) {
        
        for (AnchorSource provider : providers) {

            HashMap<SearchTarget, Anchor> map = provider.getAnchorsReplacementMap();

            for (SearchTarget target : map.keySet()) {

                if (!allReplacements.containsKey(target)) {

                    Anchor value = map.get(target);

                    if (value != null) {
                        
                        String anchor = createAnchor(value);

                        allReplacements.put(target, anchor);
                    }
                }
            }
        }

        this.sortedKeys = new ArrayList<>(allReplacements.keySet());

        this.sortedKeys.sort((t1, t2) -> t2.getTerm().length() - t1.getTerm().length());
    }

    public String scan(String html) {

        List<Mark> htmlMarkes = new HtmlTextMarker().markTexts(html);

        StringBuilder linkReplaced = new StringBuilder();

        for (Mark htmlMark : htmlMarkes) {

            String htmlChunk = htmlMark.pickChunk(html);

            if (HtmlTextMarker.MARK_TEXT.equals(htmlMark.getTag())) {

                htmlChunk = replaceLinksInHtmlChunk(htmlChunk);
            }

            linkReplaced.append(htmlChunk);
        }

        return linkReplaced.toString();
    }

    private String replaceLinksInHtmlChunk(String htmlChunk) {

        SearchAndReplaceText searchAndReplaceText
                = new SearchAndReplaceText(htmlChunk, false, true);

        for (SearchTarget target : this.sortedKeys) {

            String find = target.getTerm();

            String replacement = this.allReplacements.get(target);

            searchAndReplaceText.searchAndMarkForReplacement(find, replacement);
        }
        return searchAndReplaceText.replace();
    }

    private String createAnchor(Anchor a) {

        return "<a href = \"" + a.getHref() + "\" "
                + "title=\"" + a.getTitle() + "\" >"
                + a.getText() + "</a>";
    }

}
