package se1_prog_lab.server;

import com.google.inject.Singleton;
import se1_prog_lab.server.interfaces.ResponseBuilder;

import java.util.logging.Logger;

@Singleton
public class MyResponseBuilder implements ResponseBuilder {
    private static Logger logger = Logger.getLogger(ServerApp.class.getName());

    private String responseLine = "";

    @Override
    public void addLineToResponse(String s) {
        logger.finest("Строка \"" + responseLine + "\" добавлена в ответ");
        responseLine = responseLine + '\n' + s;
    }

    @Override
    public String getResponse() {
        return responseLine;
    }

    @Override
    public void clearResponse() {
        logger.finest("Буфер ответа очищен");
        responseLine = "";
    }
}
