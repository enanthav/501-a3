package obj;
import java.util.ArrayList;

public class CollectionObjects {
    private ArrayList<Object> list;

    public CollectionObjects() {
        list = new ArrayList<Object>();
        list.add(new ObjectClass());
        list.add(new PrimitiveClass());
    }

    public ArrayList<?> getList(){
        return list;
    }

    public void setList(ArrayList<Object> list){
        this.list = list;
    }
}