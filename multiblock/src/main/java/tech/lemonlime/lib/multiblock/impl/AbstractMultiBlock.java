package tech.lemonlime.lib.multiblock.impl;

import net.minecraft.block.Block;

public class AbstractMultiBlock extends Block {


    IDummyController controller;
    public AbstractMultiBlock(IDummyController controller,Settings settings) {
        super(settings);
        this.controller = controller;
    }











}
