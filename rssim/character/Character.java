package rssim.character;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

            // Could be edited to take in the path to the directories if changed or just immediately writing in the absolute path.
            File statFile = new File(System.getProperty("user.dir") + "/rssim/character/stats.txt");
            File levelFile = new File(System.getProperty("user.dir") + "/rssim/levels.txt");

            Scanner scStat = new Scanner(statFile);
            Scanner scLevel = new Scanner(levelFile);

            // read in level/experience values (flag = 0) then character stats (flag = 1)
            readValues(0, scLevel);
            readValues(1, scStat);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            this.validCharacter = false;
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            this.validCharacter = false;
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

    // adds gained thieving exp to this objects thievingExp field.
    // returns true if leveled up, false if not.
    public boolean addThievingExp(int expAdded)
    {
        // adds to the current exp then calculates if a new level has been gained.
        this.thievingExp = this.thievingExp + expAdded;

        // Get current level and add one. if less than 99, check if it meets or exceeds
        // ...current level + 1's exp. Do this by grabbing the exp value for the next level.
        int nextLevel = thievingLevel + 1;

        if (nextLevel <= 99)
        {
            int nextExp = expLevelVal.get(nextLevel);
            if (this.thievingExp >= nextExp) 
            {
                this.thievingLevel++;
                return true;
            }
        }

        return false;
    }

    public void editSavedExp()
    {
        // Open stats.txt, WRITE to file and update all experience values.
        File statFile = new File(System.getProperty("user.dir") + "/rssim/character/stats.txt");

        // must initialize (even to null) so we can attempt to close the file even if we don't successfully open it.
        FileWriter fw = null;
        try
        {
            // Create data to be saved to file.
            String formattedData = formatData();

            fw = new FileWriter(statFile);
            fw.write(formattedData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String formatData()
    {
        StringBuilder ret = new StringBuilder();

        // Editing each skill's saved experience values.
        ret.append("thieving=" + thievingExp + '\n');
        ret.append("combat=" + combatExp + '\n');

        return ret.toString();
    }
}
