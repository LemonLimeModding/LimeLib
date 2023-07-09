package tech.lemonlime.lib.consumables.access;

public interface UsageTimedItemStack {


    int getTickTime();
    void setTickTime(int tickTime);
    void tickUse();


}
