package com.motorcade.desktop.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Sql {
	public static void main(String[] args) {
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");
			// 建立一个数据库名motorcade.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection("jdbc:sqlite:db/motorcade.db");
			// Connection conn =
			// DriverManager.getConnection("jdbc:sqlite:path(路径)/lincj.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("create table t_tab(name varchar(64), age int);");// 创建一个表，两列
			stat.executeUpdate("insert into t_tab values('aa',12);"); // 插入数据
			stat.executeUpdate("insert into t_tab values('bb',13);");
			stat.executeUpdate("insert into t_tab values('cc',20);");

			ResultSet rs = stat.executeQuery("select * from t_tab;"); // 查询数据

			while (rs.next()) { // 将查询到的数据打印出来

				System.out.print("name = " + rs.getString("name") + " "); // 列属性一
				System.out.println("age = " + rs.getString("age")); // 列属性二
			}
			rs.close();
			conn.close(); // 结束数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
