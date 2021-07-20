package com.acidmanic.cicdassistant.controllers.models;
 
import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
 
public class FileUploadForm {
 
    public FileUploadForm() {
    }
 
    private byte[] fileData;
    private String fileName;
 
    public String getFileName() {
        return fileName;
    }
 
    @FormParam("name")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public byte[] getFileData() {
        return fileData;
    }
 
    @FormParam("file")
    @PartType("application/octet-stream")
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}