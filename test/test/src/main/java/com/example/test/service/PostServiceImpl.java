package com.example.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.test.model.Post;
import com.example.test.proxy.PostProxy;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostProxy postProxy;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String URL = "https://jsonplaceholder.typicode.com/posts";
	
	public List<Post> getPosts(String size) {
		ResponseEntity<List<Post>> response = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
		return response.getBody().subList(0, Math.min(response.getBody().size(), Integer.parseInt(size)));
	}

	public List<Post> getProxyPosts() {
		return postProxy.getPosts();
	}
	
}
