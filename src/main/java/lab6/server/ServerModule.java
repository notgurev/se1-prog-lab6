package lab6.server;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lab6.server.configs.TestServerConfig;
import lab6.server.di.factories.ServerCommandReceiverFactory;
import lab6.server.interfaces.*;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(ServerApp.class);
        bind(ServerCommandReceiver.class).to(ServerCommandReceiverImpl.class);
        bind(CollectionWrapper.class).to(VectorWrapper.class);
        bind(ResponseBuilder.class).to(MyResponseBuilder.class);
        bind(ServerConfiguration.class).to(TestServerConfig.class);
        install(new FactoryModuleBuilder()
            .implement(ServerCommandReceiver.class, ServerCommandReceiverImpl.class)
            .build(ServerCommandReceiverFactory.class));
    }

}
