package com.xmunch.facebook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.facebook.api.Post;

/**
 * @author xmunch
 * 
 */
public class FacebookFriend implements Serializable{

	private static final long serialVersionUID = -1201312535783926522L;
	private String id;
	private String name;
	private List<FacebookPost> posts = new ArrayList<FacebookPost>();

	public FacebookFriend(String id, String name) {
		this.id = id;
		this.setName(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean addPost(Post post) {
		FacebookPost facebookPost = new FacebookPost(post.getId(), post.getType()
				.toString(), post.getCreatedTime(), post.getMessage(),
				post.getLink(), post.getName(), post.getCaption(),
				post.getDescription());
		
		if(!this.posts.contains(facebookPost)){
			posts.add(facebookPost);
			return true;
		} else {
			return false;
		}
	}

	public List<FacebookPost> getPosts() {
		return posts;
	}

	public void setPosts(List<FacebookPost> posts) {
		this.posts = posts;
	}

}
