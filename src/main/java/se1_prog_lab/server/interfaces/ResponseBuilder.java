package se1_prog_lab.server.interfaces;

public interface ResponseBuilder {
    void addLineToResponse(String s);

    String getResponse();

    void clearResponse();
}
