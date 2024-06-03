package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseLoader {
	static List<String> data = new ArrayList<>();

	public static List<String> loadDataFromDatabase() {
		Connection connection;
		Statement statement;
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "qwertylang";
		try {
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			//Добавление или удаление данных из таблицы
			statement.executeUpdate("");
			//Получение данных из таблицы
			ResultSet result = statement.executeQuery("SELECT * FROM userdata;");
			//Вывод полученных данных
			while (result.next()) {
				int id = result.getInt("id");
				String col1 = result.getString("first_name");
				String col2 = result.getString("last_name");
				String col3 = result.getString("first_date");
				String col4 = result.getString("last_date");
				String col5 = result.getString("result");

				data.add("id " + id + " " + col1 + " " + col2 + " Date 1: " + col3 + " Date 2: " + col4 + " " + col5);
			}
			result.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.getMessage();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return data;
	}

	public static boolean deleteRecordFromDatabase(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "qwertylang";
		try {
			connection = DriverManager.getConnection(url, user, password);
			String sql = "DELETE FROM userdata WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			int affectedRows = statement.executeUpdate();
			statement.close();
			connection.close();
			return affectedRows > 0;
		} catch (Exception e) {
			e.getMessage();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}

	public static boolean saveDataToDatabase(String firstName, String lastName, String firstDate, String lastDate, String result) {
		Connection connection = null;
		PreparedStatement statement = null;
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "qwertylang";
		try {
			connection = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO userdata (first_name, last_name, first_date, last_date, result) VALUES (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, firstDate);
			statement.setString(4, lastDate);
			statement.setString(5, result);
			int affectedRows = statement.executeUpdate();
			statement.close();
			connection.close();
			return affectedRows > 0;
		} catch (Exception e) {
			e.getMessage();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}
}
