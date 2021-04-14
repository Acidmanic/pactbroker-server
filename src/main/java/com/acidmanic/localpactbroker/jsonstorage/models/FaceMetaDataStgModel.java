/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.jsonstorage.models;

import java.util.UUID;

/**
 *
 * @author diego
 */
public class FaceMetaDataStgModel {

    private long detectionTimestamp;
    private UUID id;

    public long getDetectionTimestamp() {
        return detectionTimestamp;
    }

    public void setDetectionTimestamp(long detectionTimestamp) {
        this.detectionTimestamp = detectionTimestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
