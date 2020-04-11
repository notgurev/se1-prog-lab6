package lab6.server;

import com.google.inject.AbstractModule;
import lab6.server.interfaces.ClientIO;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.Server;
import lab6.server.interfaces.ServerCommandReceiver;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(ServerApp.class);
        bind(ServerCommandReceiver.class).to(ServerCommandReceiverImpl.class);
        bind(CollectionWrapper.class).to(VectorWrapper.class);
        bind(ClientIO.class).to(MyClientIO.class);
    }

}
