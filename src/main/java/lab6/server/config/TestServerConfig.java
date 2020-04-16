package lab6.server.config;

import lab6.server.interfaces.ServerConfiguration;

public class TestServerConfig implements ServerConfiguration {
    private final int PORT = 3001;

    public int getPort() {
        return PORT;
    }
}
