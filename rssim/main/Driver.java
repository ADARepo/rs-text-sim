package rssim.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import rssim.character.Character;
import rssim.npc.targets.*;

public class Driver 
{

    public static void mainMenu(Character character)
    {
        Scanner userInput = new Scanner(System.in);
        int getOption = 0;

        // Used later to generate which options the player can see/pickpocket.
        int thievingLevel = character.thievingLevel;

        // Formatted String for main menu.
        String menu = "\n[1] Pickpocket a thug. (Level 1)\n" +
                        "[2] Exit game.\n";

        Thug thug = null;

        // generate option 1. exp is static for now.
        if (thievingLevel > 0)
        {
            thug = new Thug(1, 10, "Thug");
        }

        try
        {
            while (getOption != 2)
            {
                System.out.println(menu);
                
                getOption = userInput.nextInt();

                // option 1 picked, handle the Thug pickpocket.
                if (thug != null && getOption == 1)
                {
                    character.addThievingExp(thug.thievingExp);
                    System.out.println(character.thievingExp);
                }
                
            }
        }
        // If the input isnt a number.
        catch (InputMismatchException e)
        {
            // For now, just shuts down the game to be started again.
            System.out.println("\nBad input.\n");
        }

        System.out.println("\nThanks for playing!!!\n");
    }

    public static void main (String [] args)
    {
        Character player = new Character();
        mainMenu(player);

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
