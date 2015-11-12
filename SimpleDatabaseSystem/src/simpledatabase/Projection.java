package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/*
	 * The ¡§Projection¡¨ class has a next() method, when being called, it will call its child¡¦s next()
	 * to get a tuple, keeping only the attributes who are of interested (e.g., Student.Name) and
	 * discarding the rest away. Usually Projection is the root operator in the tree.
	 */
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple temp = child.next();
		
		if (temp == null) return null;
		Tuple result = temp;
		int size = temp.getAttributeList().size();
		int counter = 0;
		
		
		//System.out.println(temp.getAttributeList().get(0).getAttributeValue());
		for(int i = 0; i < size-counter; i++) {
			//System.out.println(i);
			//System.out.println(attributePredicate+"\t"+temp.getAttributeList().get(i).getAttributeName());
			if (!attributePredicate.equals(temp.getAttributeList().get(i).getAttributeName())) {
				result.attributeList.remove(i);
				counter++;
				i--;
			}
		}
		
		//System.out.println(result.getAttributeValue(0));
		return result;
	}
		

	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}