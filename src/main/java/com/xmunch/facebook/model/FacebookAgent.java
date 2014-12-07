/**
 * 
 */
package com.xmunch.facebook.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;

/**
 * @author xmunch
 * 
 */
public class FacebookAgent {

	private String lastPostId = "";
	private List<FacebookFriend> friends = new ArrayList<FacebookFriend>();

	public FacebookAgent(Facebook facebook) {
		System.out.println("Facebook agent is running");
		consumeInformation(facebook);
		System.out.println("Done");
	}

	public void consumeInformation(Facebook facebook) {
		PagedList<Post> feed = facebook.feedOperations().getHomeFeed();

		if (!feed.get(0).getId().equals(this.lastPostId)) {
			lastPostId = feed.get(0).getId();
			Iterator<Post> iterator = feed.iterator();

			while (iterator.hasNext()) {
				Post post = iterator.next();
				Integer friendIndex = addFacebookFriend(post.getFrom());

				/*
				 * In order to finish the loop when we reach an old post which
				 * has been already saved will control repeated items in the
				 * addPost method. If during the addPost execution a similar
				 * instance of "post" is found this function will stop.
				 */

				if (!this.friends.get(friendIndex).addPost(post)) {
					break;
				}
			}
		}
	}

	private Integer addFacebookFriend(Reference from) {
		FacebookFriend friend = new FacebookFriend(from.getId(), from.getName());

		Boolean repeated = false;
		Iterator<FacebookFriend> iterator = friends.iterator();

		while (iterator.hasNext()) {
			FacebookFriend item = iterator.next();
			if (item.getId().equals(friend.getId())) {
				repeated = true;
				friend = item;
			}
		}

		if (!repeated) {

			friends.add(friend);
			System.out.println("The list of friends has been updated with "
					+ friend.getName());
			System.out.println("The agent has located " + friends.size()
					+ " friends.");

		} else {

			System.out.println(friend.getName()
					+ " is already known. Any new friend node will be added.");
		}

		return friends.indexOf(friend);
	}

	public List<FacebookFriend> getFriends() {
		return friends;
	}

	public void setFriends(List<FacebookFriend> friends) {
		this.friends = friends;
	}
}
