package JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import sun.launcher.resources.launcher;


import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class TcpMysql {
	public static void main(String[] args){
		try {
			Connection conn;
			Statement stmt;
			Statement stmt1;
			ResultSet rs,rs1;
			Class<?> jdbcDriver = Class.forName("com.mysql.jdbc.Driver");
			DriverManager.registerDriver((Driver)jdbcDriver.newInstance());
			
//			String dbUrl = "jdbc:mysql://192.168.233.15:3306/studentdb?characterEncoding=gbk";
//			String dbUser = "123";
//			String dbPwd = "123";
			String dbUrl = "jdbc:mysql://222.201.101.15:3306/MYSTUDENTDB";
			String dbUser = "myroot";
			String dbPwd = "my999";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			
//			String sNameString = new String("尹川东");
//			String sClass = new String("软件工程");
			String sNameString = new String("尹川东".getBytes("GB2312"),"ISO-8859-1");
			String sClass = new String("软件工程".getBytes("GB2312"), "ISO-8859-1");
//			int result = stmt.executeUpdate("insert into students (NO,NAME,AGE,CLASS) values ('20111003632','"
//					+sNameString+"',19,'"+sClass+"')");
			
//			int result = stmt.executeUpdate("insert into boyandgirls (YOURNO,YOURNAME,AGE,CLASS) values ('20111003632','"
//			+sNameString+"',19,'"+sClass+"')");
//			System.out.println(result);
			
			rs = stmt1.executeQuery("select YOURNO,YOURNAME,AGE,CLASS from boyandgirls where YOURNO='20111003632'");
			while(rs.next()){
				String no = rs.getString(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				String classStr = rs.getString(4);
				name = new String(name.getBytes("ISO-8859-1"),"GB2312");
				classStr = new String(classStr.getBytes("ISO-8859-1"),"GB2312");
//				name = new String(name.getBytes("gbk"),"GB2312");
//				classStr = new String(classStr.getBytes("gbk"),"GB2312");
				
				System.out.println(no);
				System.out.println(name);
				System.out.println(age);
				System.out.println(classStr);
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
