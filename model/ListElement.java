/*David Fan
 * ListElement
 * Gets the HTML for a list
 */
package model;

import java.util.*;

public class ListElement extends TagElement {
	
	private ArrayList<Element> list;
	private boolean ordered;
	
	
	public ListElement(boolean ordered, java.lang.String attributes) {
		super("<ol>", true, null , attributes);
		
		list = new ArrayList<Element>();
		this.ordered = ordered;
	}

	
	public void addItem(Element item) {
		if(item != null)
			list.add(item);
	}
	
	//Nested lists ol gets replaced
	public String genHTML(int indentation) {
		String indent = getIndentation(indentation + 3);
		String toRet = super.genHTML(indentation) + "\n";
	
		//Adds to html for every entry in the list
		for(int i = 0; i < list.size(); i ++) {
			toRet+=indent + "<li>" + "\n";
			toRet+=list.get(i).genHTML(indentation + 6) + "\n";
			toRet+=indent + "</li>" + "\n";
		}
		
		toRet+=getIndentation(indentation) + getEndTag() + "\n";
		
		
		//Ensures the right tag is used for ordered vs unordered
		if(!ordered) { 
			
			String start = toRet.substring(0,toRet.indexOf(">"));
			String end = "</ul>";
			start = start.replaceAll("ol","ul");
			start += toRet.substring(toRet.indexOf(">"),toRet.length() - 6);
			start += end;
			toRet = start;
		}
			
		return toRet;
	}
	
	//Gets the amount of indentation present of a tag
	private String getIndentation(int indentation) {
		String toRet = "";
		for(int i = 0; i < indentation; i ++) {
			toRet+= " ";
		}
		return toRet;
	}
}
