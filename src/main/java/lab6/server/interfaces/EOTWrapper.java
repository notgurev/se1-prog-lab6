package lab6.server.interfaces;

public interface EOTWrapper {
    String wrap(String s);

    String unwrap(String s);

    boolean hasEOTSymbol(String s);
}
