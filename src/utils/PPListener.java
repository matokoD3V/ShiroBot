package utils;


import com.sethsutopia.utopiai.osu.events.GainedPPEvent;
import com.sethsutopia.utopiai.osu.events.OSUListener;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PPListener implements OSUListener {
    private JDA bot;
    public PPListener(JDA jda) {
        bot = jda;
    }

    public void onPlayerGainedPP(GainedPPEvent event) {
        MySQL db = new MySQL();


        ResultSet user = db.getOsuUserInfo(event.getPlayer().getUsername());
        Guild[] guilds = bot.getGuilds().toArray(new Guild[0]);
        System.out.println(">>>*** "+event.getDetails()+" ***<<<");
        try {
            while (user.next() == true) {
                if(user.getInt("toggle") == 1) { //Runs for however many times the user is in the db.
                    for(int i = 0; i < guilds.length; i++)
                    {
                        //Add text channel configuration support
                        if(guilds[i].getId().equals(user.getString("guildID"))) {
                            TextChannel[] channel = guilds[i].getTextChannels().toArray(new TextChannel[0]);
                            channel[0].sendMessage(event.getDetails()).queue();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < guilds.length; i++)
        {
            if(guilds[i].getId().equals("320329790131535873"))
                guilds[i].getTextChannelById("360937181948280843").sendMessage(event.getDetails()).queue();
        }
    }
}
