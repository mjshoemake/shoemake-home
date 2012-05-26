package mjs.database;

public class Field {

	private String name = "";
	private String type = "";
	private String format = "";
	private int maxLen = 4000;
	private boolean isPercent = false;
	private String listColumnWidth = "";
	private String caption = "";
	private boolean isLink = false;
	
	public Field() {
	}
	
	public Field(String name,
			     String type,
			     String format,
			     int maxLen,
			     boolean isPercent,
              boolean isLink,
			     String listColumnWidth,
			     String caption) {
		this.name = name;
		this.type = type;
		this.format = format;
		this.maxLen = maxLen;
		this.isPercent = isPercent;
		this.isLink = isLink;
		this.listColumnWidth = listColumnWidth;
		this.caption = caption;
	}
	
	public void setName(String value) {
		name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String value) {
		type = value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setFormat(String value) {
		format = value;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setMaxLen(int value) {
		maxLen = value;
	}
	
	public int getMaxLen() {
		return maxLen;
	}
	
	public void setIsPercent(boolean value) {
		isPercent = value;
	}
	
	public boolean getIsPercent() {
		return isPercent;
	}
	
   public void setIsLink(boolean value) {
      isLink = value;
   }
   
   public boolean getIsLink() {
      return isLink;
   }
   
	public void setListColumnWidth(String value) {
	   listColumnWidth = value;
	}
	
	public String getListColumnWidth() {
	   return listColumnWidth;
	}
	
	public void setCaption(String value) {
	   caption = value;
	}
	
	public String getCaption() {
	   return caption;
	}
}
