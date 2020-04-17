package lab6.util;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;


//делаем грязь
public class StdoutConsoleHandler extends ConsoleHandler {
    protected void setOutputStream(OutputStream out) throws SecurityException {
        super.setOutputStream(System.out);
    }
}