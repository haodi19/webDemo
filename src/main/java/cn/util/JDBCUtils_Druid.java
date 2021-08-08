package cn.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils_Druid {
    private static DataSource dataSource;

    static {
        try {
            Properties pro=new Properties();
            pro.load(new FileReader(JDBCUtils.class.getClassLoader().getResource("config.properties").toURI().getPath()));
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
