package com.chrisyee.recipeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisyee.recipeapp.model.Recipe;
import com.chrisyee.recipeapp.model.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
	
	@Query(value="SELECT * FROM recipe WHERE recipe_name = :name", nativeQuery=true)
	Recipe findRecipeByName(@Param("name") String name);
	
}
