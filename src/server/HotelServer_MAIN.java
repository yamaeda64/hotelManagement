package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Created by joakimbergqvist on 2018-03-28.
 */
public class HotelServer_MAIN
{
    public static void main(String[] args) throws IOException, SQLException
    {
        HotelServer hotelServer = new HotelServer();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        hotelServer.setLogHandler(handler);
        hotelServer.setLogLevel(Level.ALL);
        hotelServer.setPortnumber(6464);
        hotelServer.startServer();
    }
}
