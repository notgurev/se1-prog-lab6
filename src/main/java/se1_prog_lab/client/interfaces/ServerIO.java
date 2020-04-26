package se1_prog_lab.client.interfaces;

import se1_prog_lab.client.commands.Command;

public interface ServerIO {
    void open();

    void sendAndReceive(Command command);
}
