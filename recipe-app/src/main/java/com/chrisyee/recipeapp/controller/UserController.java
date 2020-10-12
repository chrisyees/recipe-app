package com.chrisyee.recipeapp.controller;

import java.util.List;

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

import com.chrisyee.recipeapp.model.Recipe;

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
	 * Find recipes that the user has selected for their cookbook
	 */
	@GetMapping(path="/cookbook/{id}")
	public List<String> findUserRecipes(@PathVariable(value = "id") Long id) {
		try{
			return userRepository.findUserRecipes(id); //uses query in repo
		} catch(Exception e) {
			System.out.println(e);
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
	 * Add Recipe to User Cookbook based on user id and recipe id
	 */
	@PostMapping(path="/cookbook/add/{user_id}/{recipe_id}")
	public void addUserRecipe(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "recipe_id") Long recipe_id) {
		userRepository.addUserRecipe(user_id, recipe_id);
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
