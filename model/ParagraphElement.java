/*David Fan
 * ParagraphElement
 * Gets the HTML for a paragraph
 */
package model;

import java.util.*;

public class ParagraphElement extends TagElement {
	
	private ArrayList<Element> elements;
	
	public ParagraphElement(String attributes) {
		super("<p>", true, null, attributes);
		elements = new ArrayList<Element>();
	}


	public String genHTML(int indentation) {
	
		String html = super.genHTML(indentation) + "\n";
		
		//Loads in all of the added elements
		for(int i = 0; i < elements.size(); i ++) {
			html+=elements.get(i).genHTML(indentation + 3) + "\n";
		}
		
		return html +getIndentation(indentation)+ getEndTag();
	}
	
	public void addItem(Element item) {
		if(item == null)
			return;
		elements.add(item);
	
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
