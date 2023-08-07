package tech.lemonlime.lib.multiblock.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import tech.lemonlime.lib.multiblock.impl.IDummyBlock;
import tech.lemonlime.lib.multiblock.impl.IDummyController;

public class MultiBlockPart extends Block {

    public IntProperty X_VALUE = IntProperty.of("main_x",-1000,1000);
    public IntProperty Y_VALUE = IntProperty.of("main_y",-1000,1000);
    public IntProperty Z_VALUE = IntProperty.of("main_z",-1000,1000);




    public MultiBlockPart(Settings settings) {
        super(settings);
    }

    public MultiBlockPos getMultiPos(BlockState state) {
        return new MultiBlockPos(state.get(X_VALUE),state.get(Y_VALUE),state.get(Z_VALUE));
    }


    @Nullable
    public IDummyController getController(BlockState state, BlockView world, BlockPos pos) {
        return world.getBlockState(getMultiPos(state).getFixed(pos)) instanceof IDummyController controller ? controller : null;
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        IDummyController controller = getController(state,world,pos);
        if (controller != null) {
            return controller.getCollisionShape(getMultiPos(state),world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state).getFixed(pos), context);
        }

        return this.collidable ? state.getOutlineShape(world, pos) : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        IDummyController controller = getController(state,world,pos);
        if (controller != null) {
            return controller.getCullingShape(getMultiPos(state),world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state).getFixed(pos));
        }

        return VoxelShapes.fullCube();
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        IDummyController controller = getController(state,world,pos);
        if (controller != null) {
            return controller.getOutlineShape(getMultiPos(state),world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state).getFixed(pos), context);
        }

        return VoxelShapes.fullCube();
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getStateForNeighborUpdate(getMultiPos(state),state,direction,neighborState,world,getMultiPos(state).getFixed(pos),neighborPos);
        }


        return state;
    }


    /**
     *I wish I could implement this right now, but I can't really :(
     */
    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return super.getSoundGroup(state);
    }


    /**
     * I REALLY WISH I COULD IMPLEMENT THIS :(
     * @return hardness, for now it just has to be set by you in block settings :(
     */
    @Override
    public float getHardness() {
        return super.getHardness();
    }

    /**
     * I REALLY WISH I COULD IMPLEMENT THIS :(
     * @return slipperyness, for now it just has to be set by you in block settings :(
     */
    @Override
    public float getSlipperiness() {
        return super.getSlipperiness();
    }

    /**
     * I REALLY WISH I COULD IMPLEMENT THIS :(
     * @return Blast Resistance, for now it just has to be set by you in block settings :(
     */
    @Override
    public float getBlastResistance() {
        return super.getBlastResistance();
    }


    /**
     * I REALLY WISH I COULD IMPLEMENT THIS :(
     * @return  Jump Velocity Multiplyer, for now it just has to be set by you in block settings :(
     */
    @Override
    public float getJumpVelocityMultiplier() {
        return super.getJumpVelocityMultiplier();
    }

    /**
     * I wish I could implement this :(
     * @return  Check if you should check for comparator output, for now it just has to overriden, (I may make another settings type that holds it in the future)
     */
    @Override
    public boolean hasComparatorOutput(BlockState state) {

        return super.hasComparatorOutput(state);
    }

    /**
     * I wish I could implement this one...
     * @return Piston Push Behavior, for now it just has to be set by you in block settings :(
     */
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return super.getPistonBehavior(state);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getPickStack(world,getMultiPos(state).getFixed(pos), world.getBlockState(getMultiPos(state)));
        }


        return new ItemStack(this);

    }


    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getComparatorOutput(world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state));
        }

        return super.getComparatorOutput(state, world, pos);
    }


    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {


        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getStrongRedstonePower(getMultiPos(state), world.getBlockState(getMultiPos(state).getFixed(pos)), world, pos, direction);
        }


        return super.getStrongRedstonePower(state, world, pos, direction);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {

        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getWeakRedstonePower(world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state), direction);
        }


        return super.getWeakRedstonePower(state, world, pos, direction);
    }


    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {

        IDummyController controller = getController(state,world,pos);

        if (controller != null) {
            return controller.getAmbientOcclusionLightLevel(world.getBlockState(getMultiPos(state).getFixed(pos)), world, getMultiPos(state).getFixed(pos));
        }

        return super.getAmbientOcclusionLightLevel(state, world, pos);
    }


}
