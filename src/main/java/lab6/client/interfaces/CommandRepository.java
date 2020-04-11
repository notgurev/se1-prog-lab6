package lab6.client.interfaces;

public interface CommandRepository {
    void runCommand(String commandKey, String[] args);
}
