package com.dbutils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多数据库的连接池,方便以后多数据库的操作
 * 
 * @author xiaose
 * 
 */
public class ConnectPool {

	private static ConnectPool instance = null;

	private ConnectPool() {
		// Exists only to defeat instantiation.
	}

	public static ConnectPool getInstance() {
		if (instance == null) {
			instance = new ConnectPool();
		}
		return instance;
	}

	private List<IdbBase> dbPools = new ArrayList<IdbBase>();

	public void addDbBase(IdbBase e) {
		for (IdbBase db : dbPools) {
			if (db.getUnique() == e.getUnique())
				return;
		}
		dbPools.add(e);
	}

	public IdbBase getDefault() {
		if (dbPools.size() > 0)
			return dbPools.get(0);
		return null;
	}

	public IdbBase getDbBase(int index) {
		if (dbPools.size() > index)
			return dbPools.get(index);
		return null;
	}
}
