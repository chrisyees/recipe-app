package com.chrisyee.recipeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisyee.recipeapp.model.User;
import com.chrisyee.recipeapp.repository.UserRepository;

/*combination of @Controller and @ResponseBody
* @Controller  : implementation classes are auto-detected from scanning
* @ResponseBody : returned objects are serialized into JSON
* 					 and passed into HTTPResponse Object
*/
@RestController 
@RequestMapping(path="/user") //URL must be /user to access
public class UserController {

	//inject an instance of the repository here
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * Get user information based on id in URL
	 */
	@GetMapping(path="/id/{id}")
	public User getUser(@PathVariable(value = "id") Long id) {
		System.out.println(userRepository.findById(id).orElse(null));
		return userRepository.findById(id).orElse(null);
	}
	
	/*
	 * Get user information based on username in URL
	 */
	@GetMapping(path="/username/{username}")
	public User findUserByUsername(@PathVariable(value = "username") String username) {
		try{
			return userRepository.findUserByUsername(username); //uses query in repo
		} catch(Exception e) {
			return null;
		}
	}
	
	/*
	 * Adds new user with request data
	 */
	@PostMapping(path="/add")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	/*
	 * Changes user log-in info based on id with request data
	 */
	@PutMapping(path="/change/{id}")
	public User changeUser(@RequestBody User oldUser, @PathVariable Long id) {
		User changedUser = userRepository.findById(id).orElse(null);
		changedUser.setUsername(oldUser.getUsername());
		changedUser.setPassword(oldUser.getPassword());
		return userRepository.save(changedUser);
	}
	
	/*
	 * Deletes user based on id
	 */
	@DeleteMapping(path="/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
		
}
