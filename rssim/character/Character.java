package rssim.character;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.Enumeration;

public class Character 
{
    public int thievingLevel;
    public int thievingExp;
    public int combatLevel;
    public int combatExp;

    public boolean validCharacter = true;

    public Dictionary <Integer, Integer> expLevelVal;

    // Constructor for the player character. Grabs character information if already created.
    public Character()
    {
        // Grab level values from levels.txt.
        // Reading in character values from stats.txt in character.
        try
        {
            expLevelVal = new Hashtable<>();

            File statFile = new File(System.getProperty("user.dir") + "/rssim/character/stats.txt");
            File levelFile = new File(System.getProperty("user.dir") + "/rssim/levels.txt");

            Scanner scStat = new Scanner(statFile);
            Scanner scLevel = new Scanner(levelFile);

            // read stats of character first, flag = 0 then levels and exp is flag = 1.
            readValues(0, scLevel);
            readValues(1, scStat);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            this.validCharacter = false;
            return;
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            this.validCharacter = false;
            return;
        }
    }

    // sets the experience value for the skills in stats.txt.
    private void skillToExpNum(String skillName, int exp)
    {
        if (skillName.equals("thieving")) this.thievingLevel = convertExpToLevel(exp);
        else if (skillName.equals("combat")) this.combatLevel = convertExpToLevel(exp);
        // else if (skillName == "") ____Exp = exp;
        // ...
    }

    // Converts experience value to skill level and stores the value.
    private int convertExpToLevel(int exp)
    {
        // Cycle through keys(levels) in the dictionary and determine the level of the character based on experience.
        Enumeration <Integer> keys = expLevelVal.keys();
        int thisKey = keys.nextElement();

        while (keys.hasMoreElements() && expLevelVal.get(thisKey) > exp) thisKey = keys.nextElement();
        
        return thisKey;
    }

    // If flag = 0, read character stats. If 1, read exp and level values.
    private void readValues(int flag, Scanner sc)
    {
        StringBuilder lhs = new StringBuilder("");
        StringBuilder rhs = new StringBuilder("");

        // Read each line in stats.txt and obtain skill name or object name (lhs) and its value (rhs).
        // Both files are formatted the same, only a difference of how to handle the information inside.
        while (sc.hasNext())
        {
            // Gets each line of the txt files and splits values by the equal sign.
            String [] thisLine = sc.next().split("[=]");
            lhs.append(thisLine[0]);
            rhs.append(thisLine[1]);

            // Can throw a number format exception if right hand isnt an integer.
            if (flag == 0) expLevelVal.put(Integer.valueOf(lhs.toString()), Integer.valueOf(rhs.toString()));
            else skillToExpNum(lhs.toString(), Integer.valueOf(rhs.toString()));

            // Reset string builder lengths to get storage for next lines values.
            lhs.setLength(0);
            rhs.setLength(0);
        }
        sc.close();
    }
}
