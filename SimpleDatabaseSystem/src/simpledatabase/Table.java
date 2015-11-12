package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	
	private String strAttribute;
	private String strType;

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		
		Tuple newTuple = null;
		try {
			
			if (!getAttribute) {
				String attribute = br.readLine();
				String type = br.readLine();
				String value = br.readLine();
				strAttribute = attribute;
				strType = type;
				newTuple = new Tuple (attribute, type, value);
				
				tuple = newTuple;
				getAttribute = true;
			}
			else {
				String checkEOF = br.readLine();
				if (!checkEOF.isEmpty()){
					newTuple = new Tuple(strAttribute, strType, checkEOF);
				}
				else {
					return null;
				}
			}
			
			newTuple.setAttributeName();
			newTuple.setAttributeType();
			newTuple.setAttributeValue();
		} catch (Exception ex) {
			return null;
		}
		//System.out.println(newTuple.getAttributeValue(1));
		return newTuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}