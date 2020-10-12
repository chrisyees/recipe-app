package com.chrisyee.recipeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisyee.recipeapp.model.User;
import com.chrisyee.recipeapp.model.Recipe;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	// ':' takes in parameter
	@Query(value="SELECT * FROM user WHERE user_username = :username", nativeQuery=true)
	User findUserByUsername(@Param("username") String username);
	
	@Query(value="SELECT recipe.recipe_name FROM recipe INNER JOIN user_has_recipe ON user_has_recipe.recipe_id = recipe.recipe_id WHERE user_has_recipe.user_id = :id", nativeQuery=true)
	List<String> findUserRecipes(@Param("id") Long id);
}
