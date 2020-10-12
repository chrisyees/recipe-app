package com.chrisyee.recipeapp.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import com.chrisyee.recipeapp.model.Ingredient;
import com.chrisyee.recipeapp.model.IngredientWrapper;
import com.chrisyee.recipeapp.repository.IngredientRepository;

@RestController
@RequestMapping(path="/ingredient")
public class IngredientController {

	@Autowired
	IngredientRepository ingredientRepository;
	
	/*
	 * Get all ingredients corresponding to a recipe id
	 */
	@GetMapping(path="/get/{id}")
	public List<Ingredient> findIngredientsForRecipe(@PathVariable(value = "id") Long id) {
		try {
			return ingredientRepository.findIngredientsForRecipe(id);
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/*
	 * Adds new single ingredient to recipe with recipe id
	 */
	@PostMapping(path="/add/single/{id}")
	public Ingredient addIngredient(@RequestBody Ingredient ingredient, @PathVariable(value = "id") Long recipe_id) {
		Ingredient i = new Ingredient();
		i.setName(ingredient.getName());
		i.setRecipeId(recipe_id);
		return ingredientRepository.save(ingredient);
	}
	
	/*
	 * Adds new ingredient list for new recipe with recipe id
	 */
	@PostMapping(path="/add/multiple/{id}")
	public void addIngredient(@RequestBody IngredientWrapper ingredient_wrapper, @PathVariable(value = "id") Long recipe_id) {
		List<String> ingredient_list = ingredient_wrapper.getIngredients();
		Collections.sort(ingredient_list);
		for(int i = 0; i < ingredient_list.size(); i++) {
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredient_list.get(i));
			newIngredient.setRecipeId(recipe_id);
			ingredientRepository.save(newIngredient);
		}
		return;
	}
	
	/*
	 * Changes ingredient list of a recipe
	 */
	@PutMapping(path="/change/{id}")
	public void changeIngredient(@RequestBody IngredientWrapper ingredient_wrapper, @PathVariable Long id) {
		List<String> ingredient_list = ingredient_wrapper.getIngredients(); //get list of new ingredients
		
		List<Ingredient> old_ingredient_list = ingredientRepository.findIngredientsForRecipe(id); //get list of old ingredients
		
		List<String> old_ingredient_list_names = new ArrayList<String>();
		for(int i = 0; i < old_ingredient_list.size(); i++) {
			old_ingredient_list_names.add(old_ingredient_list.get(0).getName());
		}
		
		List<String> ingredientsToAdd = ingredient_list;
		List<String> ingredientsToDelete = old_ingredient_list_names;
		
		//remove all old ingredient duplicates
		ingredientsToAdd.removeAll(old_ingredient_list_names);
		
		//find ingredients that aren't present anymore in new list
		ingredientsToDelete.removeAll(ingredient_list);
		
		//add new ingredients
		for(int i = 0; i < ingredientsToAdd.size(); i++) {
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredientsToAdd.get(i));
			newIngredient.setRecipeId(id);
			ingredientRepository.save(newIngredient);
		}
		
		//delete old ingredients
		for(int i = 0; i < ingredientsToDelete.size(); i++) {
			ingredientRepository.deleteIngredientByRecipeId(id, ingredientsToDelete.get(i));
		}
		
		return;
		
	}
	
}
