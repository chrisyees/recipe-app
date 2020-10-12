package com.chrisyee.recipeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chrisyee.recipeapp.model.Recipe;
import com.chrisyee.recipeapp.model.User;
import com.chrisyee.recipeapp.repository.RecipeRepository;

@RestController
@RequestMapping(path="/recipe")
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepository;
	
	/*
	 * Get recipe information based on id in URL
	 */
	@GetMapping(path="/id/{id}")
	public Recipe getUser(@PathVariable(value = "id") Long id) {
		System.out.println(recipeRepository.findById(id).orElse(null));
		return recipeRepository.findById(id).orElse(null);
	}
	
	/*
	 * Get recipe information based on name in URL
	 */
	@GetMapping(path="/name/{name}")
	public Recipe findRecipeByName(@PathVariable(value = "name") String name) {
		try{
			return recipeRepository.findRecipeByName(name); //uses query in repo
		} catch(Exception e) {
			return null;
		}
	}
	
	/*
	 * Adds new recipe with request data
	 */
	@PostMapping(path="/add")
	public Recipe addRecipe(@RequestBody Recipe recipe) {
		return recipeRepository.save(recipe);
	}
	
	/*
	 * Changes recipe info based on id with request data
	 */
	@PutMapping(path="/change/{id}")
	public Recipe changeRecipe(@RequestBody Recipe oldRecipe, @PathVariable Long id) {
		Recipe changedRecipe = recipeRepository.findById(id).orElse(null);
		changedRecipe.setName(oldRecipe.getName());
		changedRecipe.setInstructions(oldRecipe.getInstructions());
		changedRecipe.setTime_num(oldRecipe.getTime_num());
		changedRecipe.setTime_measurement(oldRecipe.getTime_measurement());
		return recipeRepository.save(changedRecipe);
	}
	
	/*
	 * Deletes recipe based on id
	 */
	@DeleteMapping(path="/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		recipeRepository.deleteById(id);
	}
	
}
