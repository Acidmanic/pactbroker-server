/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.infrastructure;

import com.acidmanic.cicdassistant.infrastructure.contracts.SshCommandExecuter;
import com.acidmanic.cicdassistant.infrastructure.contracts.SshSessionParameters;
import com.acidmanic.cicdassistant.utility.Result;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

/**
 *
 * @author diego
 */
public class MinaSshCommandExecuter implements SshCommandExecuter {

    @Override
    public Result<String> executeCommand(String command, SshSessionParameters sessionParams) {

        command = command.trim() + "\n";

        return executeCommand(
                sessionParams.getUsername(),
                sessionParams.getPassword(),
                sessionParams.getHost(),
                sessionParams.getPort(),
                10, command);

    }

    private Result<String> executeCommand(String username, String password,
            String host, int port, long defaultTimeoutSeconds, String command) {

        SshClient client = SshClient.setUpDefaultClient();

        client.start();

        try (ClientSession session = client.connect(username, host, port)
                .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {

            session.addPasswordIdentity(password);

            session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

            try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                    ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {

                channel.setOut(responseStream);

                try {

                    channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

                    try (OutputStream pipedIn = channel.getInvertedIn()) {

                        pipedIn.write(command.getBytes());

                        pipedIn.flush();
                    }

                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                            TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));

                    String responseString = new String(responseStream.toByteArray());

                    return new Result<>(true, responseString);

                } finally {
                    channel.close(false);
                }
            }
        } catch (Exception e) {

            return new Result<>(false, null);
        } finally {
            client.stop();
        }

    }

}
