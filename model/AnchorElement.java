/*David Fan
 * AnchorElement
 * Gets the HTML for a link
 */

package model;

public class AnchorElement extends TagElement {
	
	private String text;
	private String url;
	
	public AnchorElement(String url, String linkText, String attributes) {
		super("<a>", true ,new TextElement(linkText), attributes);
		url = "\"" + url + "\"";
		
		//Adds the url link as a attribute
		setAttributes("href="+ url );
		this.url = url;
		text = linkText;
	}

	public String genHTML(int indentation) { 
		String toRet = super.genHTML(indentation);
		return   toRet + getEndTag();
	}
	
	public String getLinkText() {
		return text;
	}
	
	public String getUrlText() {
		return url;
	}
	
}
