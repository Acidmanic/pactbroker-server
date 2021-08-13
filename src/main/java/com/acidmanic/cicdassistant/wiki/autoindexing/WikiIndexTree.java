/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import java.util.List;

/**
 *
 * @author diego
 */
public interface WikiIndexTree {
    
    List<WebNode> getHeads();
    
    List<WebNode> getMiscellaneousNodes();
    
    int getTotalNodesCount();
}
