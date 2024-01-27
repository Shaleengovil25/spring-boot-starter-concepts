package com.shikhakunj.udemymicroservicestutorial.user.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shikhakunj.udemymicroservicestutorial.post.Post;
import com.shikhakunj.udemymicroservicestutorial.post.PostNotFoundException;
import com.shikhakunj.udemymicroservicestutorial.post.PostRepository;
import com.shikhakunj.udemymicroservicestutorial.user.User;
import com.shikhakunj.udemymicroservicestutorial.user.UserDaoService;
import com.shikhakunj.udemymicroservicestutorial.user.UserNotFoundException;
import com.shikhakunj.udemymicroservicestutorial.user.aop.LogExecutionTime;

@RestController
public class UserJpaController {
	
//	@Autowired
//	UserDaoService service;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PostRepository postRepo;
	
	@GetMapping("/jpa/users")
	@LogExecutionTime(additionalMessage = "get all users")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	
	// for hateoas - EntityModel and WebMvcLink
	@GetMapping("/jpa/users/{id}")
	@LogExecutionTime(additionalMessage = "get a user by id")
	public EntityModel<User> getUserById(@PathVariable int id) {
		
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		EntityModel<User> entityModel  = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	@LogExecutionTime(additionalMessage = "save a user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepo.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();   
		return ResponseEntity.created(location ).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	@LogExecutionTime(additionalMessage = "delete a user by id")
	public void deleteUserById(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()){
			throw new UserNotFoundException("id: "+id);
		}
		userRepo.deleteById(id);
	}


	@PutMapping("/jpa/users/{id}")
	@LogExecutionTime(additionalMessage = "update a user by id")
	public void updateUserById(@PathVariable int id, @RequestBody User body) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()){
			throw new UserNotFoundException("id: "+id);
		}
		body.setId(id);
		userRepo.save(body);
	}

	
	@GetMapping("/jpa/users/{id}/posts")
	@LogExecutionTime(additionalMessage = "get posts for a user")
	public List<Post> retrievePostsForAUser(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	@LogExecutionTime(additionalMessage = "save post for a user")
	public ResponseEntity<Post> createPostsForAUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		post.setUser(user.get());
		Post savedPost = postRepo.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();   
		return ResponseEntity.created(location ).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{post_id}")
	@LogExecutionTime(additionalMessage = "get a post for a user")
	public EntityModel<Post> getPostsForAUserById(@PathVariable int id, @PathVariable int post_id) throws Exception{
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		else {
			Optional<Post> post = postRepo.findById(post_id);
			if(post.isEmpty()) {
				throw new PostNotFoundException("post_id: "+post_id);
			}
			EntityModel<Post> entityModel  = EntityModel.of(post.get());
			WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForAUser(id));
			entityModel.add(link.withRel("all-users"));
			
			return entityModel;
		}
		
	}
	
	@DeleteMapping("/jpa/users/{id}/posts/{post_id}")
	@LogExecutionTime(additionalMessage = "delete a post by id")
	public void deletePostById(@PathVariable int id, @PathVariable int post_id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()){
			throw new UserNotFoundException("id: "+id);
		}
		Optional<Post> post = postRepo.findById(post_id);
		if(post.isEmpty()){
			throw new UserNotFoundException("id: "+post_id);
		}
		postRepo.deleteById(post_id);
	}

}
