package tech.lemonlime.lib.multiblock;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import tech.lemonlime.lib.multiblock.old.IntHolder;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestCommand {






    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment RegEnv) {

        dispatcher.register(literal("testcmd").then(literal("convert")
                .then(argument("a", IntegerArgumentType.integer())
                .then(argument("b",IntegerArgumentType.integer())
                        .executes(context -> {
                            IntHolder holder = new IntHolder(IntegerArgumentType.getInteger(context,"a"),IntegerArgumentType.getInteger(context,"b"));
                            long lValue = holder.toLong();
                            IntHolder newHolder = IntHolder.fromLong(lValue);
                            context.getSource().sendFeedback(Text.literal(""+lValue),false);
                            context.getSource().sendFeedback(Text.literal(" and "),false);
                            context.getSource().sendFeedback(Text.literal(newHolder.getA()+" and "+newHolder.getB()),false);

                            return 0;
                        }))))
                .then(literal("revert")
                .then(argument("c", LongArgumentType.longArg())

                                .executes(context -> {
                                    IntHolder holder = IntHolder.fromLong(LongArgumentType.getLong(context,"c"));
                                    long lValue = holder.toLong();
                                    IntHolder newHolder = IntHolder.fromLong(lValue);
                                    context.getSource().sendFeedback(Text.literal(""+lValue),false);
                                    context.getSource().sendFeedback(Text.literal(" and "),false);
                                    context.getSource().sendFeedback(Text.literal(newHolder.getA()+" and "+newHolder.getB()),false);

                                    return 0;
                                })))








        );


    }
}
