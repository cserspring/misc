/*
 * File:   ItemDAO.java
 * Author: cserspring
 * ID:     swang1
 * Date:   02/09/2013
 */
package edu.cmu.hw3.dao;

import java.util.Vector;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import edu.cmu.hw3.databean.*;

public class ItemDAO extends GenericDAO<Item> {
	public ItemDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(Item.class, tableName, cp);
	}
	
	/*
	 * Given the User ID, to get the user's items.
	 * */
	public Item[] getUseItems(int userId) throws RollbackException {
		Item[] items = match();
		
		Vector<Item> v = new Vector<Item>();
		for (Item item:items)
			if(item.getUserId() == userId)
				v.add(item);
		Item[] result = new Item[v.size()];
        return v.toArray(result);
	}
}
