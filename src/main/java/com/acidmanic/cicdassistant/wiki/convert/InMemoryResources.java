/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert;

/**
 *
 * @author diego
 */
public class InMemoryResources {

    public static final String COPY_TO_CLIPBOARD_JS = "<script>\n"
            + "function copyToClipboard(id) {\n"
            + "  var copyText = document.getElementById(id);\n"
            + "  copyText.select();\n"
            + "  copyText.setSelectionRange(0, 99999)\n"
            + "  document.execCommand(\"copy\");\n"
            + "  console.log('id'+' Copied');\n"
            + "}\n"
            + "</script>";

    public static final String TOAST_COMPONENT = "<script>"
            + "function toastComponent(id){\n"
            + "    var component = document.getElementById(id);\n"
            + "    component.style.visibility='visible';\n"
            + "    component.style.opacity = 1;\n"
            + "    var fadeEffect = setInterval(function () {\n"
            + "        if (!component.style.opacity) {\n"
            + "            component.style.opacity = 1;\n"
            + "        }\n"
            + "        if (component.style.opacity > 0) {\n"
            + "            component.style.opacity -= 0.1;\n"
            + "        } else {\n"
            + "            clearInterval(fadeEffect);\n"
            + "             component.style.visibility='hidden';\n"
            + "        }\n"
            + "    }, 80);"
            + "}"
            + "</script>";
}
