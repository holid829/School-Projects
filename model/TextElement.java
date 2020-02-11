/*David Fan
 * TextElement
 * Gets the HTML for Text
 */
package model;

public class TextElement implements Element {
	String text;
	
	public TextElement(String text) {
		this.text = text;
		
	}
	
	
	@Override
	public String genHTML(int indentation) {
		String toRet = "";
		for(int i = 0; i < indentation; i ++) {
			toRet+= " ";
		}
		return toRet + text;
	}

}
