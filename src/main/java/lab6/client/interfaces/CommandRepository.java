package lab6.client.interfaces;

import lab6.client.commands.Command;

public interface CommandRepository {
    Command runCommand(String commandKey, String[] args);

    ServerIO getServerIO();

    Command parseThenRun(String[] input);
}
