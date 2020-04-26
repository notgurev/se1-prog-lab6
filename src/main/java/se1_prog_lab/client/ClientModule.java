package se1_prog_lab.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import se1_prog_lab.client.commands.AbstractCommand;
import se1_prog_lab.client.commands.concrete.*;
import se1_prog_lab.client.interfaces.Client;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.client.interfaces.CommandRepository;
import se1_prog_lab.client.interfaces.ServerIO;
import se1_prog_lab.server.UtfEOTWrapper;
import se1_prog_lab.server.interfaces.EOTWrapper;

import java.util.Scanner;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Client.class).to(ClientApp.class);
        bind(CommandRepository.class).to(CommandInvoker.class);
        bind(ClientCommandReceiver.class).to(ClientCommandReceiverImpl.class);
        bind(ServerIO.class).to(MyServerIO.class);
        bind(EOTWrapper.class).to(UtfEOTWrapper.class);

        Multibinder<AbstractCommand> commandBinder = Multibinder.newSetBinder(binder(), AbstractCommand.class);
        commandBinder.addBinding().to(Add.class);
        commandBinder.addBinding().to(Clear.class);
        commandBinder.addBinding().to(CountLessThanDescription.class);
        commandBinder.addBinding().to(ExecuteScript.class);
        commandBinder.addBinding().to(Exit.class);
        commandBinder.addBinding().to(FilterGreaterThanMinimalPoint.class);
        commandBinder.addBinding().to(Help.class);
        commandBinder.addBinding().to(History.class);
        commandBinder.addBinding().to(Info.class);
        commandBinder.addBinding().to(InsertAt.class);
        commandBinder.addBinding().to(PrintUniqueTunedInWorks.class);
        commandBinder.addBinding().to(RemoveByID.class);
        commandBinder.addBinding().to(Save.class);
        commandBinder.addBinding().to(Show.class);
        commandBinder.addBinding().to(Sort.class);
        commandBinder.addBinding().to(Update.class);
    }

    @Provides
    Scanner provideConsoleScanner() {
        return new Scanner(System.in);
    }
}
