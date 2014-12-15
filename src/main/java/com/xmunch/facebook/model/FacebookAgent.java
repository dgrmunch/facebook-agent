/**
 * 
 */
package com.xmunch.facebook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;

import com.xmunch.atomspace.aux.AtomParams;
import com.xmunch.atomspace.aux.AtomSpaceParams;
import com.xmunch.atomspace.aux.AtomType;
import com.xmunch.atomspace.aux.Globals;
import com.xmunch.atomspace.model.AtomSpace;

/**
 * @author xmunch
 * 
 */
public class FacebookAgent {

	private static final String FACEBOOK_LOGGED_USER = "fb_logged_user";
	private static final String FACEBOOK_FRIEND = "fb_friend";
	private static final String FACEBOOK_PUBLICATION = "fb_publication";
	final String HAS_POSTED = "has_posted";
	final String IS_FRIEND_OF = "friend_of";
	final String TIME = "at_time";
	final String ME = "facebook_user";
	
	private String lastPostId = "";
	private List<FacebookFriend> friends = new ArrayList<FacebookFriend>();

	public FacebookAgent(Facebook facebook) {
		consumeInformation(facebook);
		transformToAtomSpace();
	}

	private void transformToAtomSpace() {
			Iterator<FacebookFriend> friendIterator = friends.iterator();
			
			// Params
			HashMap<String, String> atomSpaceParams = new HashMap<String, String>();
	    	HashMap<String, String> atomParams = new HashMap<String, String>();
	    	atomSpaceParams.put(AtomSpaceParams.VISUALIZATION.get(),Globals.TRUE.get());
	    	atomSpaceParams.put(AtomSpaceParams.SELF.get(),Globals.FALSE.get());
	    	
	    	//API call
	    	AtomSpace atomSpace = AtomSpace.getInstance(atomSpaceParams);
	    	
	    	//Creation of the "me" node
	    	atomParams.put(AtomParams.VERTEX_LABEL.get(), ME);
			atomParams.put(AtomParams.VERTEX_TYPE.get(), FACEBOOK_LOGGED_USER);
			atomSpace.createAtom(AtomType.VERTEX.get(), atomParams);
	    	
	    	while(friendIterator.hasNext()){
	    		FacebookFriend friend = friendIterator.next();
	    		List<FacebookPost> posts = friend.getPosts();
	    		Iterator<FacebookPost> postIterator = posts.iterator();
	    		
	    		//Create friend vertex
	    		atomParams.put(AtomParams.VERTEX_LABEL.get(), friend.getName());
				atomParams.put(AtomParams.VERTEX_TYPE.get(), FACEBOOK_FRIEND);
				atomSpace.createAtom(AtomType.VERTEX.get(), atomParams);
				
				//Create me->friend edge
				atomParams.put(AtomParams.EDGE_LABEL.get(),IS_FRIEND_OF);
				atomParams.put(AtomParams.FROM.get(),ME);
				atomParams.put(AtomParams.TO.get(),friend.getName());
				atomSpace.createAtom(AtomType.EDGE.get(), atomParams);
				
				while(postIterator.hasNext()){
					FacebookPost post = postIterator.next();
					
					//Create post vertex
		    		atomParams.put(AtomParams.VERTEX_LABEL.get(), post.getId());
					atomParams.put(AtomParams.VERTEX_TYPE.get(), FACEBOOK_PUBLICATION);
					atomSpace.createAtom(AtomType.VERTEX.get(), atomParams);
					
					//Create me->friend edge
					atomParams.put(AtomParams.EDGE_LABEL.get(),HAS_POSTED);
					atomParams.put(AtomParams.FROM.get(),friend.getName());
					atomParams.put(AtomParams.TO.get(),post.getId());
					atomSpace.createAtom(AtomType.EDGE.get(), atomParams);
				}
	    	} 	
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
