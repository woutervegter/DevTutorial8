package me.moop.mytwitter;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	static final String DATABASE_NAME = "mytwitter.sqlite";
	static final int DATABASE_VERSION = 2;

	Dao<TwitterUser, String> mTwitterUsersDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, TwitterUser.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, TwitterUser.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Dao<TwitterUser, String> getTwitterUsersDao() throws SQLException {
		if (mTwitterUsersDao == null) {
			mTwitterUsersDao = getDao(TwitterUser.class);
		}
		return mTwitterUsersDao;
	}

	@Override
	public void close() {
		super.close();
		mTwitterUsersDao = null;
	}
}

