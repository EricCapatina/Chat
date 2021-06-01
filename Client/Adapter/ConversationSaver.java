package Client.Adapter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class ConversationSaver
{

    public ConversationSaver()
    {

    }

    public void SaveBooks(String name, String extension, String text)
    {
        try {
            File myObj = new File(name + extension);
            FileWriter fileWriter = new FileWriter(name + extension);
            System.out.println("File created: " + myObj.getName());
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
