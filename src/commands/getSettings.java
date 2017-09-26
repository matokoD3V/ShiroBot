package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class getSettings extends Command {
    public getSettings() {
        this.name = "settings";
        this.help = "returns current ShiroBot settings";
    }

    @Override
    protected void execute(CommandEvent event) {
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
            String query = "SELECT * FROM guilds WHERE guildID='"+event.getGuild().getId()+"';";

            // create the java statement
            PreparedStatement ps = conn.prepareStatement(query);

            // execute the query, and get a java resultset
            ResultSet rs = ps.executeQuery(query);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Settings", null, "http://lettersandnumbers.org/freealphabetletters/blue/alphabet_letter_s.jpg");
            embed.setColor(java.awt.Color.decode("#1F98E7"));

            while(rs.next()==true) {
                if(rs.getInt("ranksToggle") == 1)
                    embed.addField("Ranks", "`Enabled`", false);
                else if(rs.getInt("ranksToggle") == 0)
                    embed.addField("Ranks", "`Disabled`", false);

                if(rs.getInt("animeToggle") == 1)
                    embed.addField("MyAnimeList Command", "`Enabled`", false);
                else if(rs.getInt("animeToggle") == 0)
                    embed.addField("MyAnimeList Command", "`Disabled`", false);

                if(rs.getInt("urbanToggle") == 1)
                    embed.addField("UrbanDictionary Command", "`Enabled`", false);
                else if(rs.getInt("urbanToggle") == 0)
                    embed.addField("UrbanDictionary Command", "`Disabled`", false);
            }

            event.getTextChannel().sendMessage(embed.build()).queue();
        }

        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}