package tech.lemonlime.lib.consumables.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tech.lemonlime.lib.consumables.ConsumablesServer;
import tech.lemonlime.lib.consumables.access.UsageTimedItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements UsageTimedItemStack {
    @Unique
    private int limelib_consumables_tickTime = 0;
    @Override
    public int getTickTime() {
        return limelib_consumables_tickTime;
    }
    @Override
    public void setTickTime(int time) {
        this.limelib_consumables_tickTime = time;
    }
    @Override
    public void tickUse() {
        this.setTickTime(getTickTime()+1);
    }


    //Does not work :(
/*    @Inject(method="copy", at=@At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;setBobbingAnimationTime(I)V"),locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void limelib$consumables$addTickTimeToCopy(CallbackInfoReturnable<ItemStack> cir, ItemStack itemStack) {
        ((UsageTimedItemStack)(Object)itemStack).setTickTime(((UsageTimedItemStack) this).getTickTime());
    }*/


    @ModifyVariable(method = "copy",at = @At("STORE"))
    private ItemStack limelib$consumables$addTickTimeToCopy(ItemStack stack) {
        ((UsageTimedItemStack)(Object)stack).setTickTime(((UsageTimedItemStack) this).getTickTime());
        return stack;
    }




}
