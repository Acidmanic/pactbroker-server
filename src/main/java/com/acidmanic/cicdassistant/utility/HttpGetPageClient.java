/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 * @author diego
 */
public class HttpGetPageClient {

    public String get(String address) {

        try {

            URL url = new URL(address);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            String host = getHost(address);

            con.setRequestProperty("Host", host);

            con.setRequestProperty("User-Agent", "Acidmanic.CicdAssistant");

            InputStream input = con.getInputStream();

            String mime = con.getHeaderField("Content-Type");

            if (!mime.toLowerCase().contains("text/html")) {

                con.disconnect();

                return "";
            }

            ByteArrayOutputStream content = new ByteArrayOutputStream();

            int value = input.read();

            while (value != -1) {

                content.write(value);

                value = input.read();
            }
            input.close();
            con.disconnect();

            byte[] htmlBytes = content.toByteArray();

            return new String(htmlBytes);

        } catch (Exception e) {

            System.out.println(e);
        }
        return "";
    }

    private String getHost(String address) {

        List<String> segments = StringUtils.split(address, "/", true);

        if (segments.size() > 1) {

            if (segments.get(0).toLowerCase().startsWith("http")) {

                String protocol = segments.get(0).substring(0, segments.get(0).length() - 1).toLowerCase();

                String host = segments.get(1);

                if (!host.contains(":")) {

                    if ("https".equals(protocol)) {
                        host = host + ":443";
                    }
                }
                return host;
            }
        }
        return "";
    }
}
