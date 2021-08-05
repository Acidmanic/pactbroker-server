/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.utility.CommonRegExes;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.cicdassistant.wiki.convert.autolink.Anchor;
import java.util.List;

/**
 *
 * @author diego
 */
public class MailToAnchorSource extends DataExtractAnchorSourceBase {

    @Override
    protected Anchor createAnchor(String email) {

        Anchor anchor = new Anchor();

        anchor.setHref("mailto:" + email);

        anchor.setText(email);

        anchor.setTitle("Mail to " + email);

        return anchor;
    }

    @Override
    protected List<String> extractLinkData(String input) {

        return StringUtils.extractPatterns(input, CommonRegExes.EMAIL_REGEX);
    }

}
