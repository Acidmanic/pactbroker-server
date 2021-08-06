/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.styleproviders;

import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class CssDirectoryStyleProvider implements HtmlStyleProvider {

    private final File directory;
    private final static String DEFAULT_FLAG = "#00000000";
    private final static String DEFAULT_STYLE = "";
    private final static String DEFAULT_NAME = "No Styles";
    private final HashMap<String, String> styles;
    private final HashMap<String, String> flagCodes;

    private class ThemeCss {

        public String name;
        public String flag;
        public boolean valid = false;
    }

    public CssDirectoryStyleProvider(CssDirectoryProvider directoryProvider) {

        this.directory = directoryProvider.getCssDirectory()
                .toPath().toAbsolutePath().normalize().toFile();

        if (!this.directory.exists()) {
            this.directory.mkdirs();
        }

        this.styles = new HashMap<>();
        this.flagCodes = new HashMap<>();

        File[] files = this.directory.listFiles();

        for (File file : files) {

            ThemeCss themeCss = readMetaInfo(file);

            if (themeCss.valid) {
                String styleCode = readFile(file);

                if (!StringUtils.isNullOrEmpty(styleCode)) {

                    putStyle(themeCss.name, "#" + themeCss.flag, styleCode);
                }
            }
        }
    }

    @Override
    public List<String> getNames() {

        return new ArrayList<>(this.styles.keySet());
    }

    @Override
    public String getHeadInjectableHtml(String name) {

        return this.styles.getOrDefault(name, DEFAULT_STYLE);
    }

    @Override
    public String getStyleFlagColorCode(String name) {

        return this.flagCodes.getOrDefault(name, DEFAULT_FLAG);
    }

    @Override
    public String getDefaultName() {
        return DEFAULT_NAME;
    }

    private String readFile(File file) {

        try {
            byte[] data = Files.readAllBytes(file.toPath());

            String style = new String(data);

            return style;
        } catch (Exception e) {
        }
        return null;
    }

    private ThemeCss readMetaInfo(File file) {

        ThemeCss meta = new ThemeCss();

        meta.valid = false;

        String fileName = file.getName();

        if (fileName.toLowerCase().endsWith(".css")) {

            String nameCodeString
                    = fileName.substring(0, fileName.length() - ".css".length());

            List<String> nameCodeSegments = StringUtils.split(nameCodeString, "\\-", true);

            if (nameCodeSegments.size() == 2) {

                meta.name = nameCodeSegments.get(0);

                meta.flag = nameCodeSegments.get(1);

                meta.valid = true;
            }
        }
        return meta;

    }

    private void putStyle(String name, String flag, String style) {

        this.styles.put(name, "<style>" + style + "</style>");

        this.flagCodes.put(name, flag);
    }

}
