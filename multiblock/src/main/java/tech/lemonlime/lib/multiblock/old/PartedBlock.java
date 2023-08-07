package tech.lemonlime.lib.multiblock.old;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

import static tech.lemonlime.lib.multiblock.old.PartedBlockHelper.*;

public class PartedBlock extends Block {
    public static final IntProperty PARTS = IntProperty.of("parts", 1, 255);

    public static final LongProperty TEST = LongProperty.of("test", 1, 379551615);



//    public static final IntProperty TEST = IntProperty.of("test", 1, 18446744073709551615);

    public static final VoxelShape[] CORNER_SHAPES = Util.make(new VoxelShape[8], (cornerShapes) -> {
        cornerShapes[0] = VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.5, 0.5, 0.5);
        cornerShapes[1] = VoxelShapes.cuboid(0.5, 0.0, 0.0, 1.0, 0.5, 0.5);
        cornerShapes[2] = VoxelShapes.cuboid(0.0, 0.0, 0.5, 0.5, 0.5, 1.0);
        cornerShapes[3] = VoxelShapes.cuboid(0.5, 0.0, 0.5, 1.0, 0.5, 1.0);
        cornerShapes[4] = VoxelShapes.cuboid(0.0, 0.5, 0.0, 0.5, 1.0, 0.5);
        cornerShapes[5] = VoxelShapes.cuboid(0.5, 0.5, 0.0, 1.0, 1.0, 0.5);
        cornerShapes[6] = VoxelShapes.cuboid(0.0, 0.5, 0.5, 0.5, 1.0, 1.0);
        cornerShapes[7] = VoxelShapes.cuboid(0.5, 0.5, 0.5, 1.0, 1.0, 1.0);
    });
    public static final VoxelShape[] SHAPES = Util.make(new VoxelShape[256], (voxelShapes) -> {
        for(int i = 0; i < voxelShapes.length; ++i) {
            VoxelShape voxelShape = VoxelShapes.empty();

            for(int j = 0; j < 8; ++j) {
                if (hasCorner(i, j)) {
                    voxelShape = VoxelShapes.union(voxelShape, CORNER_SHAPES[j]);
                }
            }

            voxelShapes[i] = voxelShape.simplify();
        }

    });

    public PartedBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PARTS, 255));
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    private static boolean hasCorner(int flags, int corner) {
        return (flags & createFlag(corner)) != 0;
    }

    private static int createFlag(int corner) {
        return 1 << corner;
    }

    private static int removeCorner(int flags, int corner) {
        return flags & ~createFlag(corner);
    }

    private static boolean isFull(BlockState state) {
        return state.get(PARTS) == 255;
    }

    private static int getClosestSlice(BlockState state, Vec3d pos) {
        int i = state.get(PARTS);
        double d = Double.MAX_VALUE;
        int j = -1;

        for(int k = 0; k < CORNER_SHAPES.length; ++k) {
            if (hasCorner(i, k)) {
                VoxelShape voxelShape = CORNER_SHAPES[k];
                Optional<Vec3d> optional = voxelShape.getClosestPointTo(pos);
                if (optional.isPresent()) {
                    double e = optional.get().squaredDistanceTo(pos);
                    if (e < d) {
                        d = e;
                        j = k;
                    }
                }
            }
        }

        return j;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(PARTS)];
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PARTS);
    }





    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        int oringinalParts = state.get(PARTS);

        
        int mirroredFlags = switch (mirror) {

            case NONE -> oringinalParts;
            case LEFT_RIGHT -> mirrorLeftRight(oringinalParts);
            case FRONT_BACK -> mirrorFrontBack(oringinalParts);
        };
        
        
        return state.with(PARTS,mirroredFlags);
    }




    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {


        int shapeFlags = state.get(PARTS);

        int rotatedShapeFlags = switch (rotation) {
            case NONE -> shapeFlags;
            case CLOCKWISE_90 -> rotateClockwise(shapeFlags);
            case CLOCKWISE_180 -> rotateClockwise(rotateClockwise(shapeFlags));
            case COUNTERCLOCKWISE_90 -> rotateClockwise(rotateClockwise(rotateClockwise(shapeFlags)));
        };




        return state.with(PARTS,rotatedShapeFlags);
    }


    @Deprecated(forRemoval = true)
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);


        if(handStack.isOf(Items.DIAMOND)) {
            world.setBlockState(pos,mirror(state,BlockMirror.FRONT_BACK));
            return ActionResult.SUCCESS;
        }

        if(handStack.isOf(Items.EMERALD)) {
            world.setBlockState(pos,mirror(state,BlockMirror.LEFT_RIGHT));
            return ActionResult.SUCCESS;
        }

        if(handStack.isOf(Items.NETHERITE_INGOT)) {
            world.setBlockState(pos,rotate(state,BlockRotation.CLOCKWISE_90));
            return ActionResult.SUCCESS;
        }



        if (!handStack.isEmpty()) {
            return ActionResult.FAIL;

        }






        Vec3d vec3d = hit.getPos().subtract(pos.getX(), pos.getY(), pos.getZ());
        int i = getClosestSlice(state, vec3d);

        if (i == -1) {
            return ActionResult.FAIL;
        }
        int j = removeCorner(state.get(PARTS), i);
        if (j != 0) {
            world.setBlockState(pos, state.with(PARTS, j));
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }

        return ActionResult.SUCCESS;
    }
}