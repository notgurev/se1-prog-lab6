package se1_prog_lab.server.interfaces;

public interface EOTWrapper {
    String wrap(String s);

    String unwrap(String s);

    boolean hasEOTSymbol(String s);
}
