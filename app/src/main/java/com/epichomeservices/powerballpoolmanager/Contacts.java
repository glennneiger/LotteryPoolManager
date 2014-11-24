package com.epichomeservices.powerballpoolmanager;

public class Contacts {

	private String contactName;
	private String contactEmail;
	private boolean selected;
	
	public Contacts(String contactName, String contactEmail) {
		super();
		this.contactName = contactName;
		this.contactEmail = contactEmail;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}
	
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	
}
