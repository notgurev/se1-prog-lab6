package lab6.server;

import com.google.inject.Singleton;
import lab6.server.interfaces.ResponseBuilder;

@Singleton
public class MyResponseBuilder implements ResponseBuilder {
    private String responseLine;

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
