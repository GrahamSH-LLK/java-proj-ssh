package ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/** This examples demonstrates how a remote command can be executed. */
public class ClientWrapper {
    private static final Console con = System.console();
    private final SSHClient ssh;
    private Session session = null;

    public ClientWrapper(String host, String username) throws IOException {
        ssh = new SSHClient();
        ssh.loadKnownHosts();
        ssh.connect(host);
        ssh.authPublickey(username);

    }

    public String runCommand(String commandString) throws IOException {
        try {
            for (int i = 0; i < 2; i++){
                try {
                session = ssh.startSession(); // if doesn't succeed, try again once
                break; // if does succeed break
                } catch (Exception e) {
                    System.out.println("Failed to start session, trying again");
                }
            }
            final Command cmd = session.exec(commandString);
            return (IOUtils.readFully(cmd.getInputStream()).toString());
        } finally {

        }

    }

}
