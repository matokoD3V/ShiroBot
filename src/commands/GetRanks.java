package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import javafx.scene.paint.Color;
import net.dv8tion.jda.core.EmbedBuilder;
import utils.MySQL;
import utils.Tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class GetRanks extends Command {
    public GetRanks() {
        this.name = "ranks";
        this.help = "gets top5 leaderboard of ranks";
    }

    @Override
    protected void execute(CommandEvent event) {


        MySQL db = new MySQL();
        if(db.getToggleInfo("ranks", event.getGuild()) == 0) {
            return;
        }

        String myDriver = "org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://localhost/shirobot?autoReconnect=true&useSSL=false";

        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get("config.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = list.get(2);
        String password = list.get(3);

        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(myUrl, username, password);
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

        embed.setAuthor("Top 5 Leaderboards for " +event.getGuild().getName(), null,"http://lettersandnumbers.org/freealphabetletters/blue/alphabet_letter_l.jpg");
        embed.setColor(java.awt.Color.decode("#1F98E7"));

        Tools t = new Tools();

        for(int i = 0; i < 5; i++) {
            embed.addField("#"+(i+1)+" "+names[i] + " | Level " + levels[i], "Experience: `"+exps[i]+"/"+t.getRequiredExp(levels[i])+"`", false);
        }

        event.getTextChannel().sendMessage(embed.build()).queue();
    }
}
