package server;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Created by joakimbergqvist on 2018-03-28.
 */
public class ExmpleServerCaller_MAIN
{
    public static void main(String[] args) throws IOException
    {
        ExampleServer exampleServer = new ExampleServer();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        exampleServer.setLogHandler(handler);
        exampleServer.setLogLevel(Level.ALL);
        exampleServer.setPortnumber(6464);
        exampleServer.startServer();
    }
}
