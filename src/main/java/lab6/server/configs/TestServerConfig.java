package lab6.server.configs;

import lab6.server.interfaces.ServerConfiguration;

public class TestServerConfig implements ServerConfiguration {
  private int PORT = 3001;

  public int getPort() {
    return PORT;
  }
}
