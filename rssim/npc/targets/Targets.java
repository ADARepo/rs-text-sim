package rssim.npc.targets;

abstract class Targets 
{
    public int thievingReq;// minimum thieving level necessary to pickpocket this npc
    public int thievingExp; // exp awarded for successful pickpocket
    public String targetName;

    // constructor for setting this targets thievingRequirement and thievingExp awarded for a pickpocket.
    // also, its name.
    public Targets(int thievingReq, int thievingExp, String targetName)
    {
        this.thievingReq = thievingReq;
        this.thievingExp = thievingExp;
        this.targetName = targetName;
    }

    // was their thieving level high enough to steal from this target and succeed?
    public boolean didICatchYou(int charThievingLvl)
    {
        // generic formula for all pickpockets.
    }
}
