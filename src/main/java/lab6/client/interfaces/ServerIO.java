package lab6.client.interfaces;

import lab6.client.commands.Command;

import java.io.IOException;

public interface ServerIO {
    void sendToServer(Command command) throws IOException;
    void open() throws IOException;
    String receiveFromServer() throws IOException;
}
