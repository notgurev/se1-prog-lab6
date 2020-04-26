package lab6.server;

import com.google.inject.Singleton;
import lab6.server.interfaces.EOTWrapper;

@Singleton
public class UtfEOTWrapper implements EOTWrapper {
    private final char EOT_SYMBOL = '\u0004';

    @Override
    public String wrap(String s) {
        return s + EOT_SYMBOL;
    }

    @Override
    public String unwrap(String s) {
        if (hasEOTSymbol(s)) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Override
    public boolean hasEOTSymbol(String s) {
        return s.length() > 0 && s.charAt(s.length() - 1) == EOT_SYMBOL;
    }
}
