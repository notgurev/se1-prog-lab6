package lab6.client;

import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.client.interfaces.ServerIO;

@Singleton
public class MyServerIO implements ServerIO {
    @Override
    public void sendToServer(Command command) {
        // TODO
    }
}
