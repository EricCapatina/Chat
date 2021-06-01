package Client.Adapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtToDocSaverAdapter implements IConversationSaverAdapter
{
    private ConversationSaver _conversationSaver = new ConversationSaver();

    @Override
    public void convertAndSave(String name, String data)
    {
        try {
            File myObj = new File(name + ".doc");
            FileWriter fileWriter = new FileWriter(name + ".doc");
            System.out.println("File created: " + myObj.getName());
            fileWriter.write(data);
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
