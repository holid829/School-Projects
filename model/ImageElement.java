/*David Fan
 * ImageElement
 * Gets the HTML for a image
 */
package model;

public class ImageElement extends TagElement {
	
	public ImageElement(String imageURL, int width, int height, String alt,String attributes) {
		super("<img>", false, null, attributes);
		
		String sWidth = Integer.toString(width);
		String sHeight = Integer.toString(height);
		
		//Adds quotes to all of the attributes
		imageURL = addQuotes(imageURL);
		sWidth  = addQuotes(sWidth );
		sHeight = addQuotes(sHeight);
		alt = addQuotes(alt);
		
		setAttributes("src=" + imageURL+" "+ "width=" + sWidth +" "+"height=" + sHeight+" "+"alt=" + alt);
	}

	public String genHTML(int indentation) {
		return super.genHTML(indentation);
	}
	
	//Adds quotes to the attributes
	private String addQuotes(String toAdd) {
		String toRet ="\"";
		toRet+=toAdd;
		toRet+="\"";
		return toRet;
	}
}
