/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility.web;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class MimeTypeTable {

    private static final HashMap<String, String> MIME_TYPES = new HashMap<>();
    private static final String DEFAULT_MIME_TYPE = "text/html";

    static {
        MimeTypeTable.MIME_TYPES.put("aac", "audio/aac");
        MimeTypeTable.MIME_TYPES.put("abw", "application/x-abiword");
        MimeTypeTable.MIME_TYPES.put("arc", "application/x-freearc");
        MimeTypeTable.MIME_TYPES.put("avi", "video/x-msvideo");
        MimeTypeTable.MIME_TYPES.put("azw", "application/vnd.amazon.ebook");
        MimeTypeTable.MIME_TYPES.put("bin", "application/octet-stream");
        MimeTypeTable.MIME_TYPES.put("bmp", "image/bmp");
        MimeTypeTable.MIME_TYPES.put("bz", "application/x-bzip");
        MimeTypeTable.MIME_TYPES.put("bz2", "application/x-bzip2");
        MimeTypeTable.MIME_TYPES.put("csh", "application/x-csh");
        MimeTypeTable.MIME_TYPES.put("css", "text/css");
        MimeTypeTable.MIME_TYPES.put("csv", "text/csv");
        MimeTypeTable.MIME_TYPES.put("doc", "application/msword");
        MimeTypeTable.MIME_TYPES.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MimeTypeTable.MIME_TYPES.put("eot", "application/vnd.ms-fontobject");
        MimeTypeTable.MIME_TYPES.put("epub", "application/epub+zip");
        MimeTypeTable.MIME_TYPES.put("gz", "application/gzip");
        MimeTypeTable.MIME_TYPES.put("gif", "image/gif");
        MimeTypeTable.MIME_TYPES.put("htm", "text/html");
        MimeTypeTable.MIME_TYPES.put("html", "text/html");
        MimeTypeTable.MIME_TYPES.put("ico", "image/vnd.microsoft.icon");
        MimeTypeTable.MIME_TYPES.put("ics", "text/calendar");
        MimeTypeTable.MIME_TYPES.put("jar", "application/java-archive");
        MimeTypeTable.MIME_TYPES.put("jpeg .jpg", "image/jpeg");
        MimeTypeTable.MIME_TYPES.put("js", "text/javascript");
        MimeTypeTable.MIME_TYPES.put("json", "application/json");
        MimeTypeTable.MIME_TYPES.put("jsonld", "application/ld+json");
        MimeTypeTable.MIME_TYPES.put("mid", "audio/midi audio/x-midi");
        MimeTypeTable.MIME_TYPES.put("midi", "audio/midi audio/x-midi");
        MimeTypeTable.MIME_TYPES.put("mjs", "text/javascript");
        MimeTypeTable.MIME_TYPES.put("mp3", "audio/mpeg");
        MimeTypeTable.MIME_TYPES.put("mp4", "video/mp4");
        MimeTypeTable.MIME_TYPES.put("mpeg", "video/mpeg");
        MimeTypeTable.MIME_TYPES.put("mpkg", "application/vnd.apple.installer+xml");
        MimeTypeTable.MIME_TYPES.put("odp", "application/vnd.oasis.opendocument.presentation");
        MimeTypeTable.MIME_TYPES.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        MimeTypeTable.MIME_TYPES.put("odt", "application/vnd.oasis.opendocument.text");
        MimeTypeTable.MIME_TYPES.put("oga", "audio/ogg");
        MimeTypeTable.MIME_TYPES.put("ogv", "video/ogg");
        MimeTypeTable.MIME_TYPES.put("ogx", "application/ogg");
        MimeTypeTable.MIME_TYPES.put("opus", "audio/opus");
        MimeTypeTable.MIME_TYPES.put("otf", "font/otf");
        MimeTypeTable.MIME_TYPES.put("png", "image/png");
        MimeTypeTable.MIME_TYPES.put("pdf", "application/pdf");
        MimeTypeTable.MIME_TYPES.put("php", "application/x-httpd-php");
        MimeTypeTable.MIME_TYPES.put("ppt", "application/vnd.ms-powerpoint");
        MimeTypeTable.MIME_TYPES.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MimeTypeTable.MIME_TYPES.put("rar", "application/vnd.rar");
        MimeTypeTable.MIME_TYPES.put("rtf", "application/rtf");
        MimeTypeTable.MIME_TYPES.put("sh", "application/x-sh");
        MimeTypeTable.MIME_TYPES.put("svg", "image/svg+xml");
        MimeTypeTable.MIME_TYPES.put("swf", "application/x-shockwave-flash");
        MimeTypeTable.MIME_TYPES.put("tar", "application/x-tar");
        MimeTypeTable.MIME_TYPES.put("tif .tiff", "image/tiff");
        MimeTypeTable.MIME_TYPES.put("ts", "video/mp2t");
        MimeTypeTable.MIME_TYPES.put("ttf", "font/ttf");
        MimeTypeTable.MIME_TYPES.put("txt", "text/plain");
        MimeTypeTable.MIME_TYPES.put("vsd", "application/vnd.visio");
        MimeTypeTable.MIME_TYPES.put("wav", "audio/wav");
        MimeTypeTable.MIME_TYPES.put("weba", "audio/webm");
        MimeTypeTable.MIME_TYPES.put("webm", "video/webm");
        MimeTypeTable.MIME_TYPES.put("webp", "image/webp");
        MimeTypeTable.MIME_TYPES.put("woff", "font/woff");
        MimeTypeTable.MIME_TYPES.put("woff2", "font/woff2");
        MimeTypeTable.MIME_TYPES.put("xhtml", "application/xhtml+xml");
        MimeTypeTable.MIME_TYPES.put("xls", "application/vnd.ms-excel");
        MimeTypeTable.MIME_TYPES.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MimeTypeTable.MIME_TYPES.put("xml", "application/xml");
        MimeTypeTable.MIME_TYPES.put("xul", "application/vnd.mozilla.xul+xml");
        MimeTypeTable.MIME_TYPES.put("zip", "application/zip");
        MimeTypeTable.MIME_TYPES.put("3gp", "video/3gpp");
        MimeTypeTable.MIME_TYPES.put("3g2", "video/3gpp2");
        MimeTypeTable.MIME_TYPES.put("7z", "application/x-7z-compressed");

    }

    public String getMimeTypeForExtension(String extension) {

        String key = extension.toLowerCase();

        if (MimeTypeTable.MIME_TYPES.containsKey(key)) {
            return MimeTypeTable.MIME_TYPES.get(key);
        }

        return DEFAULT_MIME_TYPE;
    }

    public String getMimeTypeForUri(String uri) {
        int st = uri.lastIndexOf(".");

        if (st > -1 && st < uri.length()) {
            String extension = uri.substring(st + 1, uri.length());

            if (extension.length() > 0) {
                return getMimeTypeForExtension(extension);
            }
        }
        return DEFAULT_MIME_TYPE;
    }

}
