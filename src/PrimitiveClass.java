
public class PrimitiveClass {
	int integer;
	float decimal;
	boolean bool;
	
	// create a simple object with only primitives
	public PrimitiveClass(int input1, float input2, boolean input3) {
		setInteger(input1);
		decimal = input2;
		bool = input3;
	}
			
	// create an object that contains an array of primitives
	public PrimitiveClass(int input1, int input2, int input3) {
		int[] intArray = {input1, input3, input3};
	}
	

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}
	
	public boolean getBool() {
		return bool;
	}
	
	public void setBool(boolean b) {
		bool = b;
	}
	
	public float getDecimal() {
		return decimal;
	}
	
	public void setDecimal(float f) {
		decimal = f;
	}
}
