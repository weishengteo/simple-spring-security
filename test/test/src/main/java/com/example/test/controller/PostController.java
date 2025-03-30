package com.example.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Post;
import com.example.test.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	// Dependency injection
	@Autowired
	private PostService postService;
	
	// Request parameter and path variable works
	@GetMapping("/get")
	public List<Post> getPosts(@RequestParam(required = false, defaultValue="10") String size) {
		return postService.getPosts(size);
	}
	
	@GetMapping("/get/proxy")
	public List<Post> getProxyPosts() {
		return postService.getProxyPosts();
	}
}
