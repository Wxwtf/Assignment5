package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple result = child.next();
		if(result == null) return null;
		
		
		//check table match
		if(child.from.equals(whereTablePredicate)) {
			for (int i = 0; i < result.getAttributeList().size(); i++) {
				//check attribute name
				if (result.getAttributeName(i).equals(whereAttributePredicate)) {
					
					//check attribute value
					if (result.getAttributeValue(i).equals(whereValuePredicate)) {
						return result;
					}
				}
			}
			
		}
		else {
			return result;
		}
		
		return next();
			
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}