package com.chrisyee.recipeapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisyee.recipeapp.model.Ingredient;
import com.chrisyee.recipeapp.model.Recipe;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>{

	@Query(value="SELECT ingredients.ingredient_name FROM ingredients WHERE ingredients.recipe_id = :id", nativeQuery=true)
	List<Ingredient> findIngredientsForRecipe(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM ingredients WHERE recipe_id=:id AND ingredient_name = :name", nativeQuery=true)
	void deleteIngredientByRecipeId(@Param("id") Long id, @Param("name") String name);
}
