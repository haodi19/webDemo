import ServletDemo.ServletDemo2;
import cn.util.JDBCUtils;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws SQLException, IOException, ClassNotFoundException {
        HashMap<Integer,String>s=new HashMap<>();
        s.put(1,"ss");
        System.out.println(s.get(3));
    }


    public int  dd(){
        try {
            return 3;
        }catch (Exception e){
        }finally {
            System.out.println("dd");
        }
        return 5;
    }
}
