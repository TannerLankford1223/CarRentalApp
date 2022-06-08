package db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

// Implement a basic connection pool using Apache Commons DBCP
public class ConnectionPool {

    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(DBManager.getUrl());
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionPool() {

    }
}