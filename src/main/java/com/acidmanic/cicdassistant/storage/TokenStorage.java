/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.storage;

import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.models.TokenModel;
import com.acidmanic.cicdassistant.utility.Result;

/**
 *
 * @author diego
 */
public class TokenStorage extends JsonStorageBase<TokenModel> {

    public TokenStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getTokenFileStorage(), TokenModel.class, logger);
    }

    
    public boolean write(String token){
        
        TokenModel model = new TokenModel(token);
        
        return super.save(model);
    }
    
    public Result<String> read(){
        TokenModel model = super.load();
        
        if(model!=null){
            return new Result<>(true, model.getValue());
        }
        return new Result<>();
    }
    
    
    
}
