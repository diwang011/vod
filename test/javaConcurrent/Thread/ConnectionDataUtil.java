package javaConcurrent.Thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDataUtil
{
    private static String url;
    private static String user;
    private static String passwd;
    static
    {
        url = "jdbc:mysql://localhost:3306/test";
        user = "root";
        passwd = "123456";

    }

    public static void connectionData()
    {
        String sql1 = "SELECT * FROM score";
        String sql2 = "INSERT INTO score VALUES(25,905,'ENGLISH',84);";
        String sql3 = "UPDATE score SET grade=80 WHERE id=25";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, passwd);
            System.out.println("数据库连接成功！");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql1);
            while (rs.next())
            {
                int id = rs.getInt("id");
                int stu_id = rs.getInt("stu_id");
                String course = rs.getString("c_name");
                int grade = rs.getInt("grade");
                System.out.println(id + " " + stu_id + " " + course + " " + grade);
            }
            int i = stat.executeUpdate(sql2);
            if (i != 0)
            {
                System.out.println("INSERT语句执行成功!");
            }
            int j = stat.executeUpdate(sql3);
            if (j != 0)
            {
                System.out.println("UPDATE语句执行成功!");
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (stat != null)
            {
                stat.close();
                stat = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            System.out.println("没有找到数据库驱动！");
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            System.out.println("连接数据库服务器失败！");
        }
    }
}
