/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.jsonstorage.models;

/**
 *
 * @author diego
 */
public class FaceStgModel extends FaceMetaDataStgModel {

    private String faceComponentImage;
    private String facePortrateImage;

    public String getFaceComponentImage() {
        return faceComponentImage;
    }

    public void setFaceComponentImage(String faceComponentImage) {
        this.faceComponentImage = faceComponentImage;
    }

    public String getFacePortrateImage() {
        return facePortrateImage;
    }

    public void setFacePortrateImage(String facePortrateImage) {
        this.facePortrateImage = facePortrateImage;
    }

}
