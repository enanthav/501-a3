
public class ArrayObjects {
    private Object[] arrayOfObjRef;

    public ArrayObjects() {
    	arrayOfObjRef = new Object[6];
    }

    public void addObjectToArray(Object value, int i) {
        arrayOfObjRef[i] = value;
    }

}