package lab6.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lab6.client.config.ConnectionConfig;
import lab6.client.interfaces.*;
import lab6.server.UtfEOTWrapper;
import lab6.server.interfaces.EOTWrapper;

import java.util.Scanner;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Client.class).to(ClientApp.class);
        bind(CommandRepository.class).to(CommandInvoker.class);
        bind(ClientCommandReceiver.class).to(ClientCommandReceiverImpl.class);
        bind(ServerIO.class).to(MyServerIO.class);
        bind(EOTWrapper.class).to(UtfEOTWrapper.class);
        bind(ConnectionConfiguration.class).to(ConnectionConfig.class);
    }

    @Provides
    Scanner provideConsoleScanner() {
        return new Scanner(System.in);
    }
}
