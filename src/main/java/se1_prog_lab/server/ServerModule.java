package se1_prog_lab.server;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import se1_prog_lab.server.interfaces.*;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(ServerApp.class);
        bind(CollectionWrapper.class).to(VectorWrapper.class);
        bind(ResponseBuilder.class).to(MyResponseBuilder.class);
        bind(EOTWrapper.class).to(UtfEOTWrapper.class);
        install(new FactoryModuleBuilder()
                .implement(ServerCommandReceiver.class, ServerCommandReceiverImpl.class)
                .build(ServerCommandReceiverFactory.class));
    }
}
