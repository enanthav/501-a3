package obj;

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
			

	public PrimitiveClass() {
		// TODO Auto-generated constructor stub
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
