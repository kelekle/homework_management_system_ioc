package com.kle.code.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabasePool {

    private static final String ALL_URL = "jdbc:mysql://127.0.0.1:3306/class?user=kle&password=yqyforever";

//    private static volatile HikariDataSource hikariDataSource;

    private HikariDataSource hikariDataSource;

    public DatabasePool(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(ALL_URL);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public HikariDataSource getHikariDataSource(){
        return hikariDataSource;
    }

//    private DatabasePool(){
//
//    }

//    public static HikariDataSource getInstance(){
//        if(null == hikariDataSource){
//            synchronized (DatabasePool.class){
//                if(null == hikariDataSource){
//                    HikariConfig hikariConfig = new HikariConfig();
//                    hikariConfig.setJdbcUrl(ALL_URL);
//                    hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//                    hikariDataSource = new HikariDataSource(hikariConfig);
//                }
//            }
//        }
//        return hikariDataSource;
//    }


}
