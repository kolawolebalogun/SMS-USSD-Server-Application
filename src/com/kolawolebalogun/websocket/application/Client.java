package com.kolawolebalogun.websocket.application;

import com.kolawolebalogun.constants.AppConstants;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by KolawoleBalogun on 9/25/17.
 */
public class Client {

    public Session connect() {
        ClientManager client = ClientManager.createClient();
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {
            private int counter = 0;

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                counter++;
                System.out.println("### Reconnecting... (reconnectOnDisconnect count: " + counter + ")");
                return true;
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                counter++;
                System.out.println("### Reconnecting... (reconnectOnConnectionFailure count: " + counter + ") " + exception.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        };

        client.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);
        try {
            Session session = client.connectToServer(ClientEndpoint.class, new URI(AppConstants.MONITORING_TOOL_URI));
            return session;
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
