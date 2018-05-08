package controller.supportClasses;


public class ServerReplyParser
{
    public void handleMessage(String message)
    {
        System.out.println("receivedFromParser: " + message);
    }
}
