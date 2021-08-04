/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.codeparsing;

import java.util.UUID;

/**
 *
 * @author diego
 */
public class CopyBoxWrapper {
    
    
    
    public String addCopyCodeBox(String html,String originalCode){
        
        String id = UUID.randomUUID().toString();

        html = "<textArea class=\"hidden-data\" id=\"" + id + "\">" + originalCode + "</textArea>" + html;

        String messageId = UUID.randomUUID().toString();

        html = "<div class= \"code-message\" id=\"" + messageId + "\">Copied To Clipboard</div>" + html;

        html = "<div class=\"btn-copy\" "
                + "onclick=\"copyToClipboard('" + id + "');"
                + "toastComponent('" + messageId + "');\""
                + ">Copy</div>" + html;

        return html;
    }
}
