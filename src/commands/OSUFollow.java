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

public class OSUFollow extends Command {
    public OSUFollow() {
        this.name = "osufollow";
        this.help = "sets the bot to announce when a specific user gains pp";
        Permission[] perms = {Permission.MANAGE_ROLES};
        this.userPermissions = perms;
        this.arguments = "<user>";
    }

    @Override
    protected void execute(CommandEvent event) {
        MySQL db = new MySQL();

        //"INSERT INTO guilds (guildID, guildname) VALUES ('"+guildID+"', '"+event.getGuild().getName()+"');";
        //db.exQuery("INSERT INTO osuusers (username, guildID) VALUES ('"+event.getArgs()+"', '"+event.getGuild().getName()+"');");

        OSU osu = new OSU("319e7d936f399ab7663781d5bf19858fdc04b2a8");
        try {
            OSUPlayer player = osu.getUser(event.getArgs(), OSU.OsuGameMode.OSU);

            if(player == null) {
                event.getTextChannel().sendMessage("The player `"+event.getArgs()+"` doesn't exist.").queue();
                return;
            }

            ResultSet user = db.getOsuUserInfo(player.getUsername(), event.getGuild());

            int t = 0;
            int toggle = 0;
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

            if(t==0){
                db.exQuery("INSERT INTO osu_users (username, guildID, guildName) VALUES ('"+player.getUsername()+"', '"+event.getGuild().getId()+"', '"+event.getGuild().getName()+"');");
                event.getTextChannel().sendMessage("I will now announce when `" + player.getUsername() + "` gains PP.").queue();
                osu.cacheUser(event.getArgs(), OSU.OsuGameMode.OSU);
            }
            else if(t>0 && toggle == 0) {
                db.exQuery("UPDATE osu_users SET toggle = '1' WHERE username = '"+player.getUsername()+"' AND guildID = '"+event.getGuild().getId()+"';");
                event.getTextChannel().sendMessage("I will now announce when `" + player.getUsername() + "` gains PP.").queue();
                osu.cacheUser(event.getArgs(), OSU.OsuGameMode.OSU);
            }
            else {
                event.getTextChannel().sendMessage("I'm already following `"+player.getUsername()+"`.").queue();
            }

        } catch (RestfulException e) {
            e.printStackTrace();
        }
    }
}