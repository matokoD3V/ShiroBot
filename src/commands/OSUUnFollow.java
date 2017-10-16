package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sethsutopia.utopiai.osu.OSU;
import com.sethsutopia.utopiai.osu.OSUPlayer;
import com.sethsutopia.utopiai.restful.RestfulException;
import net.dv8tion.jda.core.Permission;
import utils.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OSUUnFollow extends Command {
    public OSUUnFollow() {
        this.name = "osuunfollow";
        this.help = "sets the bot to stop announcing when a specific user gains pp";
        Permission[] perms = {Permission.MANAGE_ROLES};
        this.userPermissions = perms;
        this.arguments = "<name>";
    }

    @Override
    protected void execute(CommandEvent event) {
        MySQL db = new MySQL();
        OSU osu = new OSU("319e7d936f399ab7663781d5bf19858fdc04b2a8");
        try {
            OSUPlayer player = osu.getUser(event.getArgs(), OSU.OsuGameMode.OSU);

            ResultSet user = db.getOsuUserInfo(player.getUsername(), event.getGuild());

            int t = 0;
            int toggle = 1;
            try {
                while (user.next() == true) {
                    //If t == 0, user doesn't exist in for guild in the osu_users table.
                    //   t > 0, means exists.
                    t++;
                    toggle = user.getInt("toggle");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(t == 0 || t > 0 && toggle == 0) {
                event.getTextChannel().sendMessage("I'm not following `"+player.getUsername()+"`.").queue();
            }
            else {
                db.exQuery("UPDATE osu_users SET toggle = '0' WHERE username = '"+player.getUsername()+"' AND guildID = '"+event.getGuild().getId()+"';");
                event.getTextChannel().sendMessage("I'm no longer following `"+player.getUsername()+"`.").queue();
            }

        } catch (RestfulException e) {
            e.printStackTrace();
        }
    }
}
