package in.mysite.neeraj.testing;

import java.sql.Connection;

import in.mysite.neeraj.util.Utility;

public class Testing {
	public static void main(String[] args) {
		Connection connection = Utility.getConnection();
		
		System.out.println(connection);
	}
}
