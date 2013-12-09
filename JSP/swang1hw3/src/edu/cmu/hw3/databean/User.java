/*
 * File:   User.java
 * Author: cserspring
 * ID:     swang1
 * Date:   02/09/2013
 */
package edu.cmu.hw3.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class User {
	int id;
	String email;
	String username;
	String firstname;
	String lastname;
	String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
