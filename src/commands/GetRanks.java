package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import javafx.scene.paint.Color;
import net.dv8tion.jda.core.EmbedBuilder;
import utils.MySQL;

import java.sql.*;

public class GetRanks extends Command {
    public GetRanks() {
        this.name = "ranks";
        this.help = "gets top5 leaderboard of ranks";
    }

    @Override
    protected void execute(CommandEvent event) {

        String myDriver = "org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://localhost/shirobot";
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(myUrl, "shirobot", "Inwardbend1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "(SELECT * FROM users WHERE guildID='"+event.getGuild().getId()+"' ORDER BY userExp DESC LIMIT 5) ORDER BY userExp DESC;";
        // create the java statement
        Statement st = null;
        ResultSet top10 = null;
        try {
            st = conn.createStatement();
            top10 = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        EmbedBuilder embed = new EmbedBuilder();
        int[] levels = new int[10];
        String[] names = new String[10];
        int[] exps = new int[10];
        int track = 0;

        try {
            while (top10.next() == true)
            {
                levels[track] = top10.getInt("userLevel");
                exps[track] = top10.getInt("userExp");
                names[track] = top10.getString("username");
                track++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        embed.setAuthor("Top 5 Leaderboards for " +event.getGuild().getName(), null,null);
        embed.setColor(java.awt.Color.RED);
        for(int i = 0; i < 5; i++) {
            embed.addField("#"+(i+1)+" "+names[i] + " | Level " + levels[i], "Experience: `"+exps[i]+"/"+(levels[i]*20)+"`", false);
        }

        event.getTextChannel().sendMessage(embed.build()).queue();
    }
}