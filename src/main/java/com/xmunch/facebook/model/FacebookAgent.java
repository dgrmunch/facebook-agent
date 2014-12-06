/**
 * 
 */
package com.xmunch.facebook.model;

import org.springframework.social.facebook.api.Facebook;

/**
 * @author xmunch
 * 
 */
public class FacebookAgent extends Thread {

	private Facebook facebook;
	
	public FacebookAgent(Facebook facebook) {
		this.setFacebook(facebook);
	}
	
	public void run() {
		System.out.println("Facebook agent is running");
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

}
