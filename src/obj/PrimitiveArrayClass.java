package obj;

public class PrimitiveArrayClass {
	public int[] intArray = new int[3];
    // create an object that contains an array of primitives
    public PrimitiveArrayClass(int input1, int input2, int input3) {
        intArray[0] = input1;
        intArray[1] = input2;
        intArray[2] = input3;
    }
}