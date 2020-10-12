package com.chrisyee.recipeapp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Long id;
	
	@Column(name = "recipe_name")
	private String name;
	
	@Column(name = "recipe_instructions")
	private String instructions;
	
	@Column(name = "recipe_time_num")
	private float time_num;
	
	@Column(name = "recipe_time_measurement")
	private String time_measurement;

	@ManyToMany(mappedBy = "recipes")
	private Set<User> users;
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

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

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public float getTime_num() {
		return time_num;
	}

	public void setTime_num(float time_num) {
		this.time_num = time_num;
	}

	public String getTime_measurement() {
		return time_measurement;
	}

	public void setTime_measurement(String time_measurement) {
		this.time_measurement = time_measurement;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", instructions=" + instructions + ", time_num=" + time_num
				+ ", time_measurement=" + time_measurement + "]";
	}
	
	
	
	
}
