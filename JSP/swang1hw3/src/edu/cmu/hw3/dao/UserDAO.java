/*
 * File:   UserDAO.java
 * Author: cserspring
 * ID:     swang1
 * Date:   02/09/2013
 */
package edu.cmu.hw3.dao;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import edu.cmu.hw3.databean.*;

public class UserDAO extends GenericDAO<User> {
	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(User.class, tableName, cp);
	}

	/*
	 * Given the email to find the corresponding user.
	 */
	public User read(String email) throws RollbackException {
		User[] users = match();
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email))
				return user;
		}
		return null;
	}
}
