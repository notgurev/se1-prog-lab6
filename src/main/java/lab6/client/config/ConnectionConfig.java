package lab6.client.config;

import lab6.client.interfaces.ConnectionConfiguration;

public class ConnectionConfig implements ConnectionConfiguration {
    private int PORT = 3001;
    private String HOST = "localhost";

    public int getPort() {
        return PORT;
    }

    public String getHost() {
        return HOST;
    }
}