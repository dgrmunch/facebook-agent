/**
 * 
 */
package com.xmunch.facebook.model;

import java.util.Iterator;
import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;

/**
 * @author xmunch
 * 
 */
public class FacebookAgent extends Thread {

	private Facebook facebook;
	private List<FacebookFriend> friends;
	
	public FacebookAgent(Facebook facebook) {
		this.setFacebook(facebook);
	}
	
	public void run() {
		System.out.println("Facebook agent is running");
		updateFriends();
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	private void updateFriends() {
		Reference friendPointer;
		FacebookProfile friendProfile;
		FacebookFriend friend;
		PagedList<Reference> friendsPagedList = this.facebook
				.friendOperations().getFriends();
		Iterator<Reference> iterator = friendsPagedList.iterator();

		while (iterator.hasNext()) {
			friendPointer = iterator.next();
			friendProfile = facebook.userOperations().getUserProfile(
					friendPointer.getId());
			friend = new FacebookFriend(friendProfile.getId(), friendProfile);
			if (!friends.contains(friend)) {
				friends.add(friend);
			}
		}

		System.out.println("The list of friends has been updated with " + friends.size() + " friends.");
	}

}
