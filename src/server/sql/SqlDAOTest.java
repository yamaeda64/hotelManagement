package server.sql;

import java.sql.SQLException;

public class SqlDAOTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SqlDAO dao = new SqlDAO();
		
		
		
		dao.clean();
	}
}
