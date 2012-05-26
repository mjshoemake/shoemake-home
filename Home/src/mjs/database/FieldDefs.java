package mjs.database;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class FieldDefs {

	private ArrayList items = new ArrayList();
	
	public FieldDefs() {
	}
	
	public FieldDefs(ArrayList items) {
		this.items = items;
	}
	
	public void setItems(ArrayList value) {
		items = value;
	}
	
	public ArrayList getItems() {
		return items;
	}
}
