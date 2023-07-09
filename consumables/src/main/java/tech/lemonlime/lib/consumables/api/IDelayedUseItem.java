package tech.lemonlime.lib.consumables.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IDelayedUseItem {

    /**
     * The use time (in ticks) required for an item to be fully used
     *
     * @return the use time required before the item is sent to the finished using state
     */
    int getRequiredUseTime();




    void onStartUse(World world, Entity entity, ItemStack stack);

    void onCompleteUse(World world, Entity entity, ItemStack stack);

    /**
     * Cleanup for if the item is deselected
     */
    default void onCancelUse(World world, Entity entity, ItemStack stack) {}

    /**
     * Decides if switching slots will count as a use Cancel, stopping the item from ticking its use timer.
     * @return boolean
     */
    default boolean stopsOnLostFocus() {
        return true;
    }


    default boolean canUse(World world, Entity entity, ItemStack stack) {
        return true;
    }

    default boolean forceStopUseWhenInvalid(World world, Entity entity, ItemStack stack) {
        return false;
    }


}
