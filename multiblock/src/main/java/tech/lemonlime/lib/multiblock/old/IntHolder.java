package tech.lemonlime.lib.multiblock.old;

public class IntHolder {
    int a;
    int b;

    public IntHolder(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }


    public static IntHolder fromLong(long value) {


        //int a = ((long)a << 32) | ((long)b & 0xFFFFFFFFL);
        //int b = ((long)a << 32) | ((long)b & 0xFFFFFFFFL);

        int a = (int)(value >> 32);
        int b = (int) (value & 0xFFFFFFFFL);


        return new IntHolder(a,b);
    }


    public long toLong() {

        return ((long)this.a << 32) | ((long)this.b & 0xFFFFFFFFL);
    }


}