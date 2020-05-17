package com.sr03.forumdiscussion.model;

import java.io.Serializable;

public class MessageId implements Serializable {
	private Integer id;
	private int destinationId;
	public MessageId() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	
}
