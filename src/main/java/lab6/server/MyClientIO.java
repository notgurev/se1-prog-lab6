package lab6.server;

import lab6.server.interfaces.ClientIO;
import lab6.util.BetterStrings;

public class MyClientIO implements ClientIO {
    @Override
    public void printToClient(String s) {
        // TODO
        System.out.println(BetterStrings.coloredYellow("Якобы вывод клиенту: ") + s);
    }
}
