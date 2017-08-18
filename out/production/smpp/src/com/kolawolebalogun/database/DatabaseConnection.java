package com.kolawolebalogun.database;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */

import com.kolawolebalogun.constants.AppConstants;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DatabaseConnection {
    private final static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(AppConstants.DB_DRIVER);
        dataSource.setUrl(AppConstants.DB_CONNECTION_URI);
        dataSource.setUsername(AppConstants.DB_USER);
        dataSource.setPassword(AppConstants.DB_PASS);
        dataSource.setInitialSize(250);
        dataSource.setMaxTotal(500);
        dataSource.setMaxWaitMillis(10000);
        dataSource.setMaxIdle(125);
        dataSource.setMinIdle(125);
        dataSource.setDefaultAutoCommit(false);
    }

    private DatabaseConnection() {
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
