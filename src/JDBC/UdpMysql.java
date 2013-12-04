package JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.DatabaseMetaData;

import  java.sql.ResultSetMetaData;

public class UdpMysql {

	public static void main(String[] args){
		try {
			Connection conn;
			Class<?> jdbcDriver = Class.forName("com.mysql.jdbc.Driver");
			DriverManager.registerDriver((Driver)jdbcDriver.newInstance());
			
			String dbUrl = "jdbc:mysql://222.201.101.15:3306/MYSTUDENTDB";
			String dbUser = "myroot";
			String dbPwd = "my999";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
			ResultSetMetaData rmd = resultSet.getMetaData();
			for (int i = 1; i <= rmd.getColumnCount(); i++) {
				System.out.println(rmd.getColumnLabel(i));
			}
		
			while(resultSet.next()){
				System.out.println(resultSet.getObject("TABLE_NAME"));
				resultSet.getObject("TABLE_NAME");
			}
			ResultSet fields = metaData.getColumns(null, null, "boyandgirls", null);
			
			while(fields.next()){
				System.out.println(fields.getString("COLUMN_NAME")+" "+fields.getString("TYPE_NAME")+" " + fields.getByte("column_size"));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
