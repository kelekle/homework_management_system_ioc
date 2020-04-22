package com.kle.code.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;

/**
 * @author ypb
 * 线程数据库连接绑定获取、释放工具类
 */
@Component
public class ConnectionUtils {

    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    private final DatabasePool databasePool;

    @Autowired
    public ConnectionUtils(DatabasePool databasePool) {
        this.databasePool = databasePool;
    }

    public Connection getThreadConnection(){
        try{
            //1.先从ThreadLocal上获取
            Connection conn = connectionThreadLocal.get();
            //2.判断当前线程上是否有连接
            if (conn == null) {
                //3.从数据源中获取一个连接，并且存入ThreadLocal中
                conn = databasePool.getHikariDataSource().getConnection();
                connectionThreadLocal.set(conn);
            }
            //4.返回当前线程上的连接
            return conn;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void removeConnection(){
        connectionThreadLocal.remove();
    }

}
