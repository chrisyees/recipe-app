package com.chrisyee.recipeapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ingredients")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ingredient_id")
	private Long id;
	
	@Column(name = "ingredient_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="recipe_id", nullable=false)
	private Long recipe_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRecipeId() {
		return recipe_id;
	}

	public void setRecipeId(Long recipeId) {
		this.recipe_id = recipeId;
	}
}
