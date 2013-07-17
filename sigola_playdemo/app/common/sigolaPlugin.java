package common;

import java.sql.Connection;
import java.util.List;

import models.demoModel;
import models.versionModel;

import org.hamcrest.core.Is;

import data.IdbBase;
import data.DataAction;
import data.MysqlHelper;

import play.PlayPlugin;
import play.cache.Cache;
import play.db.DB;

public class sigolaPlugin extends PlayPlugin {
	class dbBase implements IdbBase {
		@Override
		public Connection getConnection() {
			// TODO Auto-generated method stub
			return DB.getConnection();
		}
	}
	public static boolean IsReady = false;
	@Override
	public void onApplicationReady() {
		if (!IsReady) {
			MysqlHelper.idbBase = new dbBase();
			System.out.println("onApplicationReady loading mysqlhelper.DBConnection");
			IsReady = true;
		}

	}
	private static boolean isCacheVer = false;
	@Override
	public void onApplicationStart() {
		/**
		 * 启动需要的所有的数据进入缓存模块
		 */
		if (!isCacheVer) {
			DataAction action = new DataAction();

			List<versionModel> list = action.getList(versionModel.class, "select * from version");
			System.out.println(list.size());
			Cache.set("tplver", list);
			isCacheVer = true;
		}
	}

}
