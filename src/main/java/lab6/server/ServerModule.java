package lab6.server;

import com.google.inject.AbstractModule;
import lab6.server.interfaces.Server;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(ServerApp.class);
    }

}
