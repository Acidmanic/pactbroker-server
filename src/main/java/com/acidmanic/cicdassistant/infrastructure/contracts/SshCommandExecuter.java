/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.infrastructure.contracts;

import com.acidmanic.cicdassistant.utility.Result;
import java.util.List;

/**
 *
 * @author diego
 */
public interface SshCommandExecuter {

    Result<List<String>> executeCommand(String command, SshSessionParameters session);
}
