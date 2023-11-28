package rssim;

import rssim.character.Character;;

public class Driver 
{
    public static void main (String [] args)
    {
        Character player = new Character();

        // run() will execute the code inside when program is closed successfully.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() 
            {
                // Saves exp gained this session, assuming program doesn't crash.
                player.editSavedExp();
            }
        });
    } 
}
