package tech.lemonlime.lib.multiblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class MultiBlockInit implements ModInitializer {





    @Override
    public void onInitialize() {

        TestStuff.register();


        CommandRegistrationCallback.EVENT.register(TestCommand::register);


    }
}
