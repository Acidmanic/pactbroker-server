/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.cicdassistant.utility.Result;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class HtmlTemplateManager {

    private final Path templatesDirectory;

    public HtmlTemplateManager() {

        this.templatesDirectory = new ResourceHelper()
                .getExecutionDirectory()
                .resolve("templates");

        File templateDir = this.templatesDirectory.toFile();

        if (!templateDir.exists()) {

            templateDir.mkdirs();
        }

    }

    public boolean saveTemplate(String htmlTemplate, String templateName) {

        String templateFileName = getFileName(templateName);

        Path templatePath = this.templatesDirectory.resolve(templateFileName);

        try {

            File templateFile = templatePath.toFile();

            if (templateFile.exists()) {
                templateFile.delete();
            }

            Files.write(templatePath, htmlTemplate.getBytes(Charset.forName("utf-8")), StandardOpenOption.CREATE);

            return true;
        } catch (Exception e) {
        }

        return false;
    }

    public Result<String> readHtml(String templateName, HashMap<String, String> substitutions) {

        String templateFileName = getFileName(templateName);

        Path templatePath = this.templatesDirectory.resolve(templateFileName);

        try {

            String templateContent = new String(Files.readAllBytes(templatePath), Charset.forName("utf-8"));

            for (String key : substitutions.keySet()) {

                String value = substitutions.get(key);

                templateContent = templateContent.replaceAll(key, value);
            }
            return new Result<>(true, templateContent);

        } catch (Exception e) {
        }
        return new Result<>(false, null);
    }

    private String getFileName(String name) {

        char[] chars = name.toLowerCase().toCharArray();

        StringBuilder sb = new StringBuilder();

        for (char c : chars) {

            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                sb.append(c);
            } else if (Character.isWhitespace(c)) {
                sb.append("-");
            } else {
                sb.append("[").append((int) c).append("]");
            }
        }
        return sb.toString() + ".html";
    }
}
