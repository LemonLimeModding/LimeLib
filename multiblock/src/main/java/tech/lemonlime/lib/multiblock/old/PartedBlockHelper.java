package tech.lemonlime.lib.multiblock.old;

import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public class PartedBlockHelper {


    static int mirrorLeftRight(int cubeData) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            int bit = (cubeData >> i) & 1;
            result |= (bit << (8 - i - 1));
        }
        return result;
    }

    static int mirrorFrontBack(int cubeData) {
        return (cubeData &0b00001111) << 4 | (cubeData &0b11110000) >> 4;
    }

    static int rotateClockwise(int cubeData) {
        return ((cubeData & 0b1100) >> 2) | ((cubeData & 0b0011) << 2);
    }

    private static int rotateUp(int cubeData) {
        return ((cubeData & 0b1111) << 4) | ((cubeData & 0b11110000) >> 4);
    }

    private static int rotateDown(int cubeData) {
        return ((cubeData & 0b0011) << 2) | ((cubeData & 0b1100) >> 2);
    }











}
