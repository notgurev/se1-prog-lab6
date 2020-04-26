package lab6.server.interfaces;

public interface ResponseBuilder {
    void addLineToResponse(String s);

    String getResponse();

    void clearResponse();
}
