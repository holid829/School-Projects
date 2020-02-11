/*David Fan
 *HeaderElement
 * Gets the HTML for a header
 */
package model;

public class HeadingElement extends TagElement {
	public HeadingElement(Element content, int level, String attributes) {
		//The tag will change depending o nlevel of header
		super("<h" + level + ">", true, content, attributes);
	}

	public String genHTML(int indentation) {
		return super.genHTML(indentation) + getEndTag();
	}
}
