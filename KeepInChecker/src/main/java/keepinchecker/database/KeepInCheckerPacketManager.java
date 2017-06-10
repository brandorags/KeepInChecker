package keepinchecker.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.KeepInCheckerPacket;

public class KeepInCheckerPacketManager {

	private ConnectionSource connectionSource;
	private Dao<KeepInCheckerPacket, Long> packetDao;
	
	public void savePackets(List<KeepInCheckerPacket> packets) throws SQLException, IOException {
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			packetDao = DaoManager.createDao(connectionSource, KeepInCheckerPacket.class);
			
			packetDao.create(packets);
		} finally {
			connectionSource.close();
		}
	}
	
	public List<KeepInCheckerPacket> getPackets() throws SQLException, IOException {
		List<KeepInCheckerPacket> packets = null;
		
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			packetDao = DaoManager.createDao(connectionSource, KeepInCheckerPacket.class);
			
			packets = packetDao.queryForAll();
		} finally {
			connectionSource.close();
		}
		
		return packets;
	}
	
}
