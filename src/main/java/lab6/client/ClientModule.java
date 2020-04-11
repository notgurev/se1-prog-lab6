package lab6.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lab6.client.interfaces.Client;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;

import java.util.Scanner;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Client.class).to(ClientApp.class);
        bind(CommandRepository.class).to(CommandInvoker.class);
        bind(ClientCommandReceiver.class).to(ClientCommandReceiverImpl.class);
    }

    @Provides
    Scanner provideConsoleScanner() {
        return new Scanner(System.in);
    }
}
