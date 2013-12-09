package model;


import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.User;

public class UserDAO extends GenericDAO<User> {

	public UserDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(User.class, tableName, connectionPool);
	}

	public User[] getUsers() throws RollbackException {
		User[] users = match();
		return users;
	}
	
	public User getUserByUsername(String username) {
		User[] list = null;
		try {
			list = match(MatchArg.equals("username", username));
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		if (list.length == 0)
			return null;
		return list[0];
	}
	
	public void setNewPassword(int userId, String newPassword) throws RollbackException{
		try {
        	Transaction.begin();
			User dbUser = read(userId);
			
			if (dbUser == null) {
				throw new RollbackException("This user no longer exists");
			}
			
			dbUser.setPassword(newPassword);
			
			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
