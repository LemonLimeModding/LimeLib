package tech.lemonlime.lib.multiblock.impl;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.VineBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import tech.lemonlime.lib.multiblock.api.MultiBlockPos;

public interface IDummyController {


    default VoxelShape getCollisionShape(MultiBlockPos multiPos, BlockState state, BlockView world, BlockPos ownerPos, ShapeContext context) {
        return state.getOutlineShape(world, ownerPos);
    }

    default VoxelShape getCullingShape(MultiBlockPos multiPos, BlockState blockState, BlockView world, BlockPos ownerPos) {
        return this.getOutlineShape(multiPos, blockState, world, ownerPos, ShapeContext.absent());
    }

    default VoxelShape getOutlineShape(MultiBlockPos multiPos, BlockState blockState, BlockView world, BlockPos ownerPos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    default BlockState getStateForNeighborUpdate(MultiBlockPos multiPos, BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos fixed, BlockPos neighborPos) {
        return state;
    }

    default ItemStack getPickStack(BlockView world, BlockPos fixed, BlockState state) {
        return state.getBlock().getPickStack(world, fixed, state);
    }

    default int getComparatorOutput(BlockState blockState, World world, BlockPos pos) {
        return 0;
    }

    default int getWeakRedstonePower(BlockState blockState, BlockView world, BlockPos pos, Direction direction) {
        return 0;
    }

    default int getStrongRedstonePower(MultiBlockPos multiPos, BlockState blockState, BlockView world, BlockPos pos, Direction direction) {
        return 0;
    }

    default float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return state.isFullCube(world, pos) ? 0.2F : 1.0F;
    }
}
