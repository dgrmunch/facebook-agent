package com.xmunch.facebook.model;

import java.util.Date;

public class FacebookPost {
	
	String id;
	String type;
	String message;
	String link;
	String name;
	String caption;
	String description;
	Date time;

	public FacebookPost(String id, String type, Date time,
			String message, String link, String name, String caption,
			String description) {
		this.id = id;
		this.type = type;
		this.message = message;
		this.link = link;
		this.name = name;
		this.caption = caption;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
