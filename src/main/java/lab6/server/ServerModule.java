package lab6.server;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lab6.server.config.ServerConfig;
import lab6.server.interfaces.*;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(ServerApp.class);
        bind(CollectionWrapper.class).to(VectorWrapper.class);
        bind(ResponseBuilder.class).to(MyResponseBuilder.class);
        bind(ServerConfiguration.class).to(ServerConfig.class);
        bind(EOTWrapper.class).to(UtfEOTWrapper.class);
        install(new FactoryModuleBuilder()
                .implement(ServerCommandReceiver.class, ServerCommandReceiverImpl.class)
                .build(ServerCommandReceiverFactory.class));
    }
}
