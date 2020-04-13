package lab6.client.network.configs;

import lab6.client.interfaces.ConnectionConfiguration;

public class TestConnectionConfig implements ConnectionConfiguration {
  private int PORT = 3001;
  private String HOST = "localhost";

  public int getPort() {
    return PORT;
  }

  public String getHost() {
    return HOST;
  }
}