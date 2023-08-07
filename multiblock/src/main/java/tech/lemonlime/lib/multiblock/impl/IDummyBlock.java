package tech.lemonlime.lib.multiblock.impl;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

public interface IDummyBlock {


    BlockPos getOwnerPos();
    Block getOwnerBlock();

    VoxelShape getShape();



}
