/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.storage;

import java.io.File;

/**
 *
 * @author diego
 */
public interface StorageFileConfigs {
    
    
    File getPactFileStorage();
    File getTokenFileStorage();
    File getBadgesFileStorage();
    File getPactMapFileStorage();
    
}
