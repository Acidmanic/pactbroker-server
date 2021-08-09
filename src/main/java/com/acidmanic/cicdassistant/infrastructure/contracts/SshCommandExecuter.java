/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.infrastructure.contracts;

import com.acidmanic.cicdassistant.utility.Result;

/**
 *
 * @author diego
 */
public interface SshCommandExecuter {

    Result<String> executeCommand(String command, SshSessionParameters session);
}
