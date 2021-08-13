/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import com.acidmanic.cicdassistant.utility.StringUtils;

/**
 *
 * @author diego
 */
public class Script extends ContainerHtmlTagBase {

    public Script() {

        setAttribute("language", "javascript");
    }

    public String getSrc() {
        return getAttribute("src");
    }

    public void setSrc(String src) {

        this.setAttribute("src", src);

        if (StringUtils.isNullOrEmpty(src)) {

            this.removeAttribute("src");
            
        } else {

            this.setAttribute("crossorigin", "anonymous");
        }
    }

    public String getIntegrity() {

        return this.getAttribute("integrity");
    }

    public void setIntegrity(String integrity) {

        this.setAttribute("integrity", integrity);
    }

}
