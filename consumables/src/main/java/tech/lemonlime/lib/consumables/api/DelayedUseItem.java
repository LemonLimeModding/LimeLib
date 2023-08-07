package tech.lemonlime.lib.consumables.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import tech.lemonlime.lib.consumables.access.UsageTimedItemStack;

public abstract class DelayedUseItem extends Item implements IDelayedUseItem {



    public DelayedUseItem(Settings settings) {
        super(settings);
    }
    @Override
    public void inventoryTick(ItemStack normalStack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;

        DelayedUseItem item = (DelayedUseItem) normalStack.getItem();
        UsageTimedItemStack useStack = (UsageTimedItemStack)(Object) normalStack;

        if (item.forceStopUseWhenInvalid(world,entity,normalStack)
                && !item.canUse(world,entity,normalStack)) {
            useStack.setTickTime(0);
            return;
        }

        if ((!item.stopsOnLostFocus() || (selected || slot == PlayerInventory.OFF_HAND_SLOT || slot == 0))) {
            int newTickTime = useStack.getTickTime()+1;
            if (newTickTime <= 1) return;
            useStack.setTickTime(newTickTime);

            if (newTickTime >= item.getRequiredUseTime()) {
                item.onCompleteUse(world,entity, normalStack);
                useStack.setTickTime(0);
            }

        } else if (useStack.getTickTime() > 0) {
            useStack.setTickTime(0);
            item.onCancelUse(world,entity, normalStack);
        }



    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack rawStack = player.getStackInHand(hand);
        UsageTimedItemStack heldStack = (UsageTimedItemStack)(Object)(rawStack);


        if (!world.isClient() &&
                rawStack.getItem() instanceof DelayedUseItem heldItem
                && heldItem.canUse(world,player,rawStack)
                && heldStack.getTickTime() <= 0) {
            heldStack.tickUse();
            heldItem.onStartUse(world,player,rawStack);
        }


        return ItemUsage.consumeHeldItem(world,player,hand);
    }


}
