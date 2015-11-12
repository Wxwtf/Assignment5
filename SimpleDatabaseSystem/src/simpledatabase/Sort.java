package simpledatabase;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	/*
	 * The ¡§Sort¡¨ class has a next() method, when being called, it will call its child¡¦s next() to
	 * fetch all tuples and sort them.
	 */
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple result;
		Type ty;
		int indexOrderKey = 0;
		if (!tuplesResult.isEmpty()) return tuplesResult.remove(0);
		
		result = child.next();
		if (result == null) return null;
		
		
		for (int i=0; i < result.getAttributeList().size();i++) {
			if (result.getAttributeName(i).equals(orderPredicate)) {
				indexOrderKey=i;
				ty = result.getAttributeType(i);
				break;
			}
		}
		
		while (result != null) {
			tuplesResult.add(result);
			result = child.next();
		}
		
		//System.out.println(tuplesResult.get(0).getAttributeType(1).type);
		
		for (int i = 0; i < tuplesResult.size(); i++) {
			Tuple t1 = tuplesResult.get(i);
			for (int j = 0; j < tuplesResult.size(); j++) {
				Tuple t2 = tuplesResult.get(j);
				if (t1.getAttributeType(indexOrderKey).type.equals(Type.DataTypes.STRING)) {
					String temp1 = t1.getAttributeValue(indexOrderKey).toString();
					String temp2 = t2.getAttributeValue(indexOrderKey).toString();
					if (temp1.compareToIgnoreCase(temp2) < 0) {
						//swap t1 and t2
						Collections.swap(tuplesResult, i, j);
					}
				}
				else {
					BigDecimal n1 = new BigDecimal(t1.getAttributeValue(indexOrderKey).toString());
					BigDecimal n2 = new BigDecimal(t2.getAttributeValue(indexOrderKey).toString());
					if (n1.compareTo(n2) < 0) {
						//swap n1 and n2
						Collections.swap(tuplesResult, i, j);
					}
				}
			}
		}
		
		/*
		for (int i = 0; i < tuplesResult.size(); i++) {
			for (int j=0; j<tuplesResult.get(i).getAttributeList().size();j++) {
				System.out.print(tuplesResult.get(i).getAttributeValue(j)+" ");
			}
			System.out.println();
		}
		*/
		result = tuplesResult.remove(0);
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