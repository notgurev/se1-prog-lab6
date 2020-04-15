package lab6.server.interfaces;

public interface EOTWrapper {
    public String wrap(String s);
    public String unwrap(String s);
    public boolean hasEOTSymbol(String s);
}
