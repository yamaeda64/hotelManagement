package server.sql;

import java.sql.SQLException;

public class SqlDAOTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SqlDAO dao = new SqlDAO();
		System.out.println(dao.getAllRooms());
		
		System.out.println(dao.findFreeRooms("VAXJO", "SINGLE", false, false, "1522886400000", "1523145600000"));
		
		//System.out.println(dao.allBookings("0", "1554096000000"));
		
		dao.clean();
	}
}
