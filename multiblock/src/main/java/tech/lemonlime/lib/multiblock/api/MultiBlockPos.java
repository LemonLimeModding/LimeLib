package tech.lemonlime.lib.multiblock.api;

import net.minecraft.util.math.BlockPos;

public class MultiBlockPos extends BlockPos {
    public MultiBlockPos(int i, int j, int k) {
        super(i, j, k);
    }


    public BlockPos getFixed(BlockPos pos) {
        return pos.add(this);
    }

    public BlockPos getRelative() {
        return this;
    }


}
