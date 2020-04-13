package lab6.server;

import lab6.server.interfaces.ResponseBuilder;

public class MyResponseBuilder implements ResponseBuilder {
    String responseLine;

    public MyResponseBuilder() {
        responseLine = ""; // я не помню нужно ли это)))
    }

    @Override
    public void addLineToResponse(String s) {
        responseLine = responseLine + '\n' + s;
    }

    @Override
    public String getResponse() {
        return responseLine;
    }

    @Override
    public void clearResponse() {
        responseLine = "";
    }
}
