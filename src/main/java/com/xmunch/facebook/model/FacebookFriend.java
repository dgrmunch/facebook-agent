package com.xmunch.facebook.model;

import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author xmunch
 * 
 */
public class FacebookFriend {
	
	private String id;
	private FacebookProfile profile;
	
	
	public FacebookFriend(String id, FacebookProfile profile) {
		this.id = id;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public FacebookProfile getProfile() {
		return profile;
	}
	
	public void setProfile(FacebookProfile profile) {
		this.profile = profile;
	}
	
	
	
}
