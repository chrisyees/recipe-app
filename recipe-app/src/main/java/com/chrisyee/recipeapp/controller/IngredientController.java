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
import com.chrisyee.recipeapp.repository.IngredientRepository;
import com.chrisyee.recipeapp.repository.RecipeRepository;

@RestController
@RequestMapping(path="/ingredient")
public class IngredientController {

	@Autowired
	IngredientRepository ingredientRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
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
		i.setRecipe(recipeRepository.findById(recipe_id).orElse(null));
		return ingredientRepository.save(i);
	}
	
	/*
	 * Adds new ingredient list for new recipe with recipe id (should only be called with new recipes)
	 * TODO: check if ingredient exists first before adding ingredient
	 */
	@PostMapping(path="/add/multiple/{id}")
	public void addIngredient(@RequestBody List<Ingredient> ingredients_to_add, @PathVariable(value = "id") Long recipe_id) {
		//Get ingredient list supplied by request
		List<Ingredient> ingredient_list = new ArrayList<>();
		ingredient_list.addAll(ingredients_to_add);
		
		//extract names from ingredient list
		List<String> ingredient_list_names = new ArrayList<>();
		for(int i = 0; i < ingredient_list.size(); i++) {
			ingredient_list_names.add(ingredient_list.get(i).getName());
		}
		
		for(int i = 0; i < ingredient_list_names.size(); i++) {
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredient_list_names.get(i));
			newIngredient.setRecipe(recipeRepository.findById(recipe_id).orElse(null));
			ingredientRepository.save(newIngredient);
		}
		return;
	}
	
	/*
	 * Changes ingredient list of a recipe
	 */
	@PutMapping(path="/change/{id}")
	public void changeIngredient(@RequestBody List<Ingredient> ingredients_to_change, @PathVariable(value = "id") Long recipe_id) {

		//get list of ingredients sent from request
		List<Ingredient> ingredient_list = new ArrayList<>(); 
		ingredient_list.addAll(ingredients_to_change);
		
		List<String> ingredient_list_names = new ArrayList<String>();
		for(int i = 0; i < ingredient_list.size(); i++) {
			ingredient_list_names.add(ingredient_list.get(i).getName());
		}
		
		//find existing ingredients for recipe
		List<Ingredient> old_ingredient_list = ingredientRepository.findIngredientsForRecipe(recipe_id); //get list of old ingredients

		List<String> old_ingredient_list_names = new ArrayList<String>();
		for(int i = 0; i < old_ingredient_list.size(); i++) {
			old_ingredient_list_names.add(old_ingredient_list.get(i).getName());
		}

		//create to lists, one of ingredients to delete and one of ingredients to add
		List<String> ingredientsToAdd = new ArrayList<>();
		ingredientsToAdd.addAll(ingredient_list_names);
		
		List<String> ingredientsToDelete = new ArrayList<>();
		ingredientsToDelete.addAll(old_ingredient_list_names);

		//remove all old ingredient duplicates
		ingredientsToAdd.removeAll(old_ingredient_list_names);
		
		//find ingredients that aren't present anymore in new list
		ingredientsToDelete.removeAll(ingredient_list_names);
	
		//add new ingredients
		for(int i = 0; i < ingredientsToAdd.size(); i++) {
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredientsToAdd.get(i));
			newIngredient.setRecipe(recipeRepository.findById(recipe_id).orElse(null));
			ingredientRepository.save(newIngredient);
		}
		
		//delete old ingredients
		for(int i = 0; i < ingredientsToDelete.size(); i++) {
			ingredientRepository.deleteIngredientByRecipeId(recipe_id, ingredientsToDelete.get(i));
		}
		
		return;
	}
	
}
