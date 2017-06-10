package keepinchecker.database;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;

public class UserManager {
	
	private ConnectionSource connectionSource;
	private Dao<User, Integer> userDao;
	
	public void saveUser(User user) throws SQLException, IOException {
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			userDao = DaoManager.createDao(connectionSource, User.class);
			
			userDao.createOrUpdate(user);	
		} finally {
			connectionSource.close();
		}
		
		// update the cached user
		Constants.USER = getUser();
	}

	public User getUser() throws SQLException, IOException {
		User user = null;
		
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			userDao = DaoManager.createDao(connectionSource, User.class);
			
			// we should only ever have one user
			user = userDao.queryForId(1);
		} finally {
			connectionSource.close();
		}
		
		return user;
	}

}
