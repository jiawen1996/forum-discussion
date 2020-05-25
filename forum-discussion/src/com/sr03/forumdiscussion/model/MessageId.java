package com.sr03.forumdiscussion.model;

import java.io.Serializable;

public class MessageId implements Serializable {
	private int id;
	private int destinationId;

	public MessageId() {

	}

	public MessageId(int destinationId) {
		this.destinationId = destinationId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

}
