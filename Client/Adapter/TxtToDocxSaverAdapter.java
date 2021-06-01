package Client.Adapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtToDocxSaverAdapter implements IConversationSaverAdapter
{
    private ConversationSaver _conversationSaver = new ConversationSaver();

    @Override
    public void convertAndSave(String name, String data)
    {
        try {
            File myObj = new File(name + ".docx");
            FileWriter fileWriter = new FileWriter(name + ".docx");
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
