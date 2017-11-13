import obj.*;
import java.util.Scanner;
public class ObjectCreator {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int selection = 0;
		System.out.println("Select an option from below: ");
		System.out.println("[1] Object with Primitive Variables");
		System.out.println("[2] Object that contains References to Other Objects");
		System.out.println("[3] Object that Contains a Three Integer Array");
		System.out.println("[4] Object that Contains an Array of References");
		System.out.println("[5] Object using Collection Instance\n");
		
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			System.err.println("Invalid number, please try again");
		}
		
		if (selection == 1) {
			System.out.println("Input an integer: ");
			int inputInt = input.nextInt();
			System.out.println("Input a float: ");
			float inputFloat = input.nextFloat();
			System.out.println("True or false: you like cats");
			boolean inputBool = input.nextBoolean();
			PrimitiveClass primClass = new PrimitiveClass(inputInt, inputFloat, inputBool);
			
			System.out.println(primClass.getInteger() + " " + primClass.getDecimal() + " " + primClass.getBool());
			// serialize here
		} else if (selection == 2) {
			System.out.println("Input an integer: ");
			int inputInt = input.nextInt();
			ObjectRef objReferences = new ObjectRef();
			objReferences.setIntVar(inputInt);
			System.out.println("Object that contains a reference to other objects created");
			// serialize
			// ensure circular references are handled
		} else if (selection == 3) {
			System.out.println("Input three integers: ");
			System.out.println("First integer: ");
			int inputInt1 = input.nextInt();
			System.out.println("Second integer: ");
			int inputInt2 = input.nextInt();
			System.out.println("Third integer: ");
			int inputInt3 = input.nextInt();
			PrimitiveArrayClass primArrayObj = new PrimitiveArrayClass(inputInt1, inputInt2, inputInt3);
			// serialize
		} else if (selection == 4) {
			System.out.println("Object that contains an array of object references created");
			Object obj1 = new Object();
			Object obj2 = new Object();
			// object that contains an array of object references
			ArrayObjects arrayObjectRefs = new ArrayObjects();
			arrayObjectRefs.addObjectToArray(obj1, 0);
			arrayObjectRefs.addObjectToArray(obj1, 2);
		} else if (selection == 5) {
			System.out.println("Object that contains an instance of Java's collection classes created");
			// create objects to insert
			Object obj1 = new Object();
			Object obj2 = new Object();
			// object that uses instance of Java's collection classes
			ArrayObjects arrayObj = new ArrayObjects();
			arrayObj.addObjectToArray(obj1, 0);
			arrayObj.addObjectToArray(obj2, 1);
			// serialize
		} else {
			System.out.println("Invalid number, please try again");
		}
	}

}
