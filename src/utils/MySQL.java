package utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class MySQL {

    /*
    This is used for the Listener class.
     */

    public ResultSet getMemberInfo(String userID, Guild guild) {
        ResultSet rs = null;
        try {
            List<String> list = Files.readAllLines(Paths.get("config.txt"));
            String username = list.get(2);
            String password = list.get(3);

            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/shirobot?autoReconnect=true&useSSL=false";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, username, password);

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM users WHERE userID='"+userID+"' AND guildID='"+guild.getId()+"';";

            // create the java statement
            PreparedStatement ps = conn.prepareStatement(query);

            // execute the query, and get a java resultset
            rs = ps.executeQuery(query);
            return rs;
        }

        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return rs;
    }


    public void exQuery(String query) {
        try {
            List<String> list = Files.readAllLines(Paths.get("config.txt"));
            String username = list.get(2);
            String password = list.get(3);

            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/shirobot?autoReconnect=true&useSSL=false";

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, username, password);


            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
        }

        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
