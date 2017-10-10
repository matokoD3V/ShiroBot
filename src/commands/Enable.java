package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Enable extends Command {
    public Enable() {
        this.name = "enable";
        this.help = "enables features of ShiroBot";
    }

    @Override
    protected void execute(CommandEvent event) {
        if(!event.getMember().getUser().getId().equals("235995758896087041"))
            return;

        String target = event.getMessage().getContent().substring(9, event.getMessage().getContent().length());

        try {
            List<String> list = Files.readAllLines(Paths.get("config.txt"));
            String username = list.get(2);
            String password = list.get(3);

            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/shirobot?autoReconnect=true&useSSL=false";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, username, password);


            String query = "UPDATE guilds SET "+target.toLowerCase()+"Toggle = '1' WHERE guildID='"+event.getGuild().getId()+"';";


            // create the java statement
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute(query);
            event.getTextChannel().sendMessage("Successfully enabled `" + target + "`.").queue();
        }

        catch (Exception e)
        {
            event.getTextChannel().sendMessage("There was an issue enabling `" + target + "`.").queue();
            System.err.println("Got an exception! ");
            //System.err.println(e.getMessage());
        }
    }
}