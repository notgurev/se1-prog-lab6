package lab6.client.interfaces;

import lab6.client.commands.Command;

public interface ServerIO {
    void sendToServer(Command command);
}
