package tech.lemonlime.lib.multiblock.old;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashMap;

public class ShapeGroup {

    private final HashMap<Direction, ShapeSupplier> shapes;



    private ShapeGroup(HashMap<Direction, ShapeSupplier> shapes) {
        this.shapes = shapes;
    }

    VoxelShape getShape(Direction direction,BlockState state) {
        return this.shapes.getOrDefault(direction, ((dir, st) -> VoxelShapes.empty())).get(direction,state);
    }





    public class Builder {


        private HashMap<Direction, ShapeSupplier> shapes;
        public Builder() {
            shapes = new HashMap<>();
        }

        public Builder setNorth(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.NORTH, shapeSupplier);
            return this;
        }
        public Builder setSouth(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.SOUTH, shapeSupplier);
            return this;
        }
        public Builder setEast(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.EAST, shapeSupplier);
            return this;
        }
        public Builder setWest(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.WEST, shapeSupplier);
            return this;
        }
        public Builder setUp(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.UP, shapeSupplier);
            return this;
        }
        public Builder setDown(ShapeSupplier shapeSupplier) {
            this.shapes.put(Direction.DOWN, shapeSupplier);
            return this;
        }

        public ShapeGroup build() {
            return new ShapeGroup(shapes);
        }


    }




    public interface ShapeSupplier {
        VoxelShape get(Direction dir, BlockState state);

    }



}
