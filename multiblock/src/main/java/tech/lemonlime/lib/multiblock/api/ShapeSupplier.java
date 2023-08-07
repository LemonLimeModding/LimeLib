package tech.lemonlime.lib.multiblock.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public interface ShapeSupplier {
    VoxelShape get(Direction dir, BlockState state);
}
