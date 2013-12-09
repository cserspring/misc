package model;

import java.util.Vector;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Item;

public class ItemDAO extends GenericDAO<Item> {

	public ItemDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(Item.class, tableName, connectionPool);
	}

	public Item[] getItems() throws RollbackException {
		// Calls GenericDAO's match() method.
		// This no match constraint arguments, match returns all the Item beans
		Item[] items = match();
		return items;
	}
	
	public void create(Item item) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(item);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public Item[] getItemsByUserId(int userId) {
		try {
			Item[] list = match(MatchArg.equals("userId", userId));
			return list;
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Item[] search(String keywords) {
		try{
			if (keywords == null || keywords.length() == 0)
				return null;
			String[] query = keywords.split(" ");
			Item[] items = match();
			Vector<Item> result = new Vector<Item>();
			for (int i = 0; i < items.length; i++) {
				for (int j = 0; j < query.length; j++) {
					if (!items[i].getDescription().contains(query[j])) {
						break;
					}
					if (j == query.length - 1)
						result.add(items[i]);
				}
			}
			Item[] rs = new Item[result.size()];
			return result.toArray(rs);
		} catch (Exception e){
			return null;
		}
	}
}
