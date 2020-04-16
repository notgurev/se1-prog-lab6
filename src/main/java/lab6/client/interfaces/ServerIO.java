package lab6.client.interfaces;

import lab6.client.commands.Command;

import java.io.IOException;

public interface ServerIO {
    boolean isOpen();

    void sendToServer(Command command) throws IOException;
    void open();
    String receiveFromServer() throws IOException;
}
