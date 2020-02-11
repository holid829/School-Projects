/*David Fan
 * Webpage
 * Sets up general HTML and adds in any added elements
 */
package model;

import java.util.*;

public class WebPage {

	private static boolean genID = true;
	private String title;
	private ArrayList<Element> elements;
	
	public WebPage(String title){
		this.title = title;
		elements = new ArrayList<Element>();
	}
	
	
	public int addElement(Element element) {
		elements.add(element);
		TagElement ID;
		
		//Checks to make sure the element is a tag before returning a ID
		if(element instanceof TagElement)
			ID = (TagElement)element;
		else
			return -1;
		
		return ID.getId();
	}
	
	
	public int compareTo(WebPage webPage) {
		return title.compareTo(webPage.title);
	}
	
	public static void enableId(boolean choice) {
		genID = choice;
	}
	
	public Element findElem(int id) {
		
		//Iterates through elements to search for the ID
		for(int i = 0; i < elements.size(); i ++) {
			Element current = elements.get(i);
			if(current instanceof TagElement)
				if(((TagElement) current).getId() == id)
					return current;
		}
		return null;
	}
	
	
	public String getWebPageHTML(int indentation) {
		
		String indent ="   ";
		String toRet = "<!doctype html>\n";
		
		toRet+= "<html>\n" + indent + "<head>\n";
		toRet+=indent + indent + "<meta charset=\"utf-8\"/>\n";
		toRet+=indent + indent + " <title>"+title+"</title>\n";
		toRet+= indent + "</head>\n" + indent+"<body>\n";
	
		//Adds the genHTML's of all the elements in elements
		for(int i = 0; i < elements.size();i++) {
			toRet += elements.get(i).genHTML(indentation);
		}

		toRet+=indent + "</body>\n"+"</html>\n";	
		
		return toRet;
	}
	
	public String stats() {
		int tcount = 0;
		int lcount = 0;
		int pcount = 0;
		double totalUsage = 0;
		
		//Counts all of the table, list, and paragraph elements
		for(int i = 0; i < elements.size(); i ++) {
			if(elements.get(i) instanceof TableElement) {
				tcount++;
				totalUsage += ((TableElement) elements.get(i)).getTableUtilization();

			}
			else if(elements.get(i) instanceof ListElement)
				lcount++;
			else if(elements.get(i) instanceof ParagraphElement)
				pcount++;
		}
		

		
		return "List Count: "+lcount+"\n"+"Paragraph Count: "+pcount+"\n"+"Table Count: "+tcount+"\n"+"TableElement Utilization: "+totalUsage/tcount;
	}
	
	void writeToFile(String filename, int indentation) {
		Utilities.writeToFile(filename,getWebPageHTML(indentation));
	}
}
