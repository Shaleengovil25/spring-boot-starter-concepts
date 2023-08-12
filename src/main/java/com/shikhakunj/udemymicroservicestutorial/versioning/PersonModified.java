package com.shikhakunj.udemymicroservicestutorial.versioning;

public class PersonModified {
	
	private Name name;

	public PersonModified(Name name) {
		super();
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	

}
