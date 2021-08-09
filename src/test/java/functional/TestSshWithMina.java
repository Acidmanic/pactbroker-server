/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
public class TestSshWithMina {

    public static void main(String[] args) throws IOException {

        String command = "ls -a ..\n";

        String response = listFolderStructure("exampleuser", "53(ure", "localhost", 22, 10, command);

        System.out.println("Reponse: " + response);

    }

    public static String listFolderStructure(String username, String password,
            String host, int port, long defaultTimeoutSeconds, String command) throws IOException {

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

                    return responseString;

                } finally {
                    channel.close(false);
                }
            }
        } finally {
            client.stop();
        }
    }
}
