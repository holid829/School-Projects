/*David Fan
 * TableElement
 * Gets the HTML for a Table
 */
package model;

public class TableElement extends TagElement {

	private int r;
	private int c;
	private Element[][] table;
	
	
	public TableElement(int rows, int cols, String attributes) {
		super("<table>", true, null, attributes);
		r = rows;
		c = cols;
		//A table that represents the elements added
		table = new Element[r][c];
	}

	
	public void addItem(int rowIndex,int colIndex,Element item) {
		table[rowIndex][colIndex] = item;
	}
	
	
	public String genHTML(int indentation) {
		String toRet = super.genHTML(indentation) + "\n";
		String indent = getIndentation(indentation + 3);
		
		//For every index in the table, either a empty tag is generated or a tag with a element is added
		for(int i = 0; i < r; i ++) {
			
			toRet +=indent+"<tr>";
			
			for(int j = 0; j < c; j ++) {
				toRet +="<td>";
				
				if(table[i][j]!=null) {
					toRet+=table[i][j].genHTML(0);
				
				}
				
				toRet +="</td>";
			}
			
			toRet+="</tr>" + "\n";
		}
		
		
		toRet+=getIndentation(indentation) + getEndTag() +"\n";
		
		return toRet;
	}
	
	
	public double getTableUtilization() {
		int count = 0;
		
		//Counts the amount of slots used in the table
		for(int i = 0; i < r; i ++) 
			for(int j = 0; j < c; j ++) 
				if(table[i][j]!=null)
					count++;
	
		return (double)count/(r*c)*100;
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
