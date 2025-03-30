package com.example.test.service;

import java.util.List;

import com.example.test.model.Post;

public interface PostService {
	public List<Post> getPosts(String size);
	
	public List<Post> getProxyPosts();
}
