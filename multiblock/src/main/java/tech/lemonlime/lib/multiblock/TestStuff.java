package tech.lemonlime.lib.multiblock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tech.lemonlime.lib.multiblock.old.PartedBlock;

public class TestStuff {


    public static Block TEST_BLOCK = registerBlock("test_block", new PartedBlock(AbstractBlock.Settings.of(Material.STONE)));

    public static Item TEST_BLOCK_ITEM = registerItem("test_block", new BlockItem(TEST_BLOCK, new FabricItemSettings().fireproof()));

    public static void register() {

    }



    private static Block registerBlock(String name,Block block) {
        return Registry.register(Registries.BLOCK, new Identifier("multiblock",name),block);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("multiblock",name),item);
    }
}
