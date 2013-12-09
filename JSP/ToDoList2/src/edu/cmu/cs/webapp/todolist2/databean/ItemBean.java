package edu.cmu.cs.webapp.todolist2.databean;

public final class ItemBean {
	private int    id;
	private String item;
	private int    position;
	private String ipAddress;
	private User   user;
	
	public int    getId()                { return id;           }
    public String getItem()              { return item;         }
    public int    getPosition()          { return position;     }
    public String getIpAddress()         { return ipAddress;    }
    public User   getUser()              { return user;         }

    public void   setId(int i)           { id = i;              }
	public void   setItem(String s)      { item = s;            }
	public void   setPosition(int i)     { position = i;        }
	public void   setIpAddress(String s) { ipAddress = s;       }
	
	public void   setUser(User u)        { user = u;            }
}
