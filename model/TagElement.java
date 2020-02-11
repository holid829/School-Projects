/*David Fan
 * TagElement
 * A overall class that returns basic html structure
 */
package model;


public class TagElement implements Element {

	private static boolean genID = true;
	private static int idCount = 1;
	private int thisId;
	private String tag;
	private boolean endT;
	private String text;
	private int endLoc;
	
	public TagElement(String tagName,boolean endTag,Element content,String attributes) {
		tag = tagName;
		endT = endTag;
		text ="";
		
		//endLoc finds the location of the end >, this location is used to input future attributes
		endLoc = tagName.indexOf(">");
		
		
		if(content != null)
			text = content.genHTML(0);
		
		//Assigns a ID to this tag
		if(genID) {
			thisId = idCount;
			idCount++;
		}
		
		setAttributes(attributes);
		
	}
	
	
	public static void	resetIds() {
		idCount = 1;		
	}

	public static void	enableId(boolean choice) {
		genID = choice;
	}
	
	
	public String genHTML(int indentation) {
	
		//If ID's are active, setAttributes can be used to set the tagName and id at the front of the tag
		if(genID) 
			setAttributes("id=" + addQuotes(getStringId()));
		
		String toRet = getIndentation(indentation);
		
		
		toRet += tag;
		toRet +=text;
		return toRet;
	}

	
	public void setAttributes(String attributes) {
		
		if(attributes == null)
			return;
	
		//Attributes are added to the location one space past the end of the tagName
		String temp = tag.substring(endLoc);
		tag = tag.substring(0,endLoc);
		tag = tag + " " + attributes + temp;
	}
	
	
	public String getEndTag() {
		if(endT) {
			String temp = tag.substring(1,endLoc);
			temp = "</" + temp + ">";
			return temp;
		}
		return null;
	}
	
	public String getStartTag() {
		return tag;
	}
	
	public String getStringId() {
		
		if(!genID)
			return null;
		//Gets the tagname without < or >
		return tag.substring(1,endLoc) + thisId;
	}
	
	public int getId() {
		if(!genID)
			return -1;
		return thisId;
	}
	
	//Adds quotes to the attributes
	private String addQuotes(String toAdd) {
		
		String toRet ="\"";
		toRet+=toAdd;
		toRet+="\"";
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
