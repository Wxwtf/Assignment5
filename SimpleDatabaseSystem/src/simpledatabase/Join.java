package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	
	private int indexOrgKey;
	private int indexJoinKey;
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	
	/*
	 * The Join class has a next() method, when being called, it will call its children next() to
	 * get tuples to find which pair of tuples could be joined and join them.
	 */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple newTuple = null;
		Tuple orgTuple = leftChild.next();
		Tuple joinTuple = null;
		boolean escFlag = false;
		joinTuple = rightChild.next();
		
		if(joinTuple == null) return null;
		
		if (orgTuple != null) {
			for (indexOrgKey=0;indexOrgKey<orgTuple.getAttributeList().size();indexOrgKey++) {
				for (indexJoinKey=0;indexJoinKey<joinTuple.getAttributeList().size();indexJoinKey++) {
					if (orgTuple.getAttributeName(indexOrgKey).equals(joinTuple.getAttributeName(indexJoinKey))) {
						escFlag = true;
						break;
					}
				}
				if(escFlag)	break;
			}
		}
		
		while (orgTuple != null) {
			//System.out.println("pass f");
			if (orgTuple.getAttributeValue(indexOrgKey).equals(joinTuple.getAttributeValue(indexJoinKey))) {
				newTuple = orgTuple;
				for (int j=0; j<joinTuple.getAttributeList().size();j++) {
					if (j != indexJoinKey) {
						newTuple.attribute = joinTuple.getAttributeList().get(j);
						newTuple.addAttriubteList();
					}
				}
				break;
			}
			else {
				tuples1.add(orgTuple);
			}
			
			orgTuple = leftChild.next();
		}
		
		
		for (int i=0; i<tuples1.size();i++) {
			
			if(joinTuple.getAttributeValue(indexJoinKey).equals(tuples1.get(i).getAttributeValue(indexOrgKey))) {
				newTuple = tuples1.get(i);
				for (int j=0; j<joinTuple.getAttributeList().size();j++) {
					if (j != indexJoinKey) {
						newTuple.attribute = joinTuple.getAttributeList().get(j);
						newTuple.addAttriubteList();
					}
				}
				tuples1.remove(i);
				break;
			}
		}
		
		return newTuple;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}