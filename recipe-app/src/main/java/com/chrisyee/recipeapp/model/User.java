package com.chrisyee.recipeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Model for User Accounts
 * Data: Id, Username, Password
 */

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long user_id; //variables must be same name as column, or use @Column annotation
	
	String user_username;
	
	String user_password;

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	@Override
	public String toString() {
		return "User [id=" + user_id + ", user_username=" + user_username + ", user_password=" + user_password + "]";
	}
	
}
