package com.example.test.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.test.model.Post;

@FeignClient(name="post-client", url = "https://jsonplaceholder.typicode.com")
public interface PostProxy {
	
	@GetMapping("/posts")
	List<Post> getPosts();
}
