package utils;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Listener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {



        System.out.println(event.getGuild().getName() + " | " + event.getTextChannel().getName() + " | " + event.getMember().getEffectiveName() + ": " + event.getMessage().getContent());
        if(event.getMember().getUser().isBot())
            return;
        if(event.getMember().getUser().getId().equals("213400627038912523")) {
            event.getTextChannel().sendMessage("shut the fuck up kid").queue();
            return;
        }

        MySQL db = new MySQL();

        if(db.getToggleInfo("ranks", event.getGuild()) == 1) {
            ResultSet memberinfo = db.getMemberInfo(event.getMember().getUser().getId(), event.getGuild());
            int memberExp = 0;
            int memberOrigLevel = 0;
            int memberLevel = 0;
            int requiredExp = 0;
            try {
                while (memberinfo.next() == true) {
                    memberLevel = memberinfo.getInt("userLevel");
                    memberExp = memberinfo.getInt("userExp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Tools t = new Tools();
            memberOrigLevel = memberLevel;
            memberExp += ThreadLocalRandom.current().nextInt(15, 25 + 1);
            requiredExp = t.getRequiredExp(memberLevel);

            while (memberExp > requiredExp) {
                memberLevel++;
                requiredExp = t.getRequiredExp(memberLevel);
            }

            if (memberOrigLevel < memberLevel) {
                event.getTextChannel().sendMessage("" + event.getMember().getEffectiveName() + " has leveled up to Lvl." + memberLevel + "!").queue();
            }

            db.exQuery("UPDATE users SET userLevel=" + memberLevel + ", userExp=" + memberExp + " WHERE userID='" + event.getMember().getUser().getId() + "' AND guildID='" + event.getGuild().getId() + "';");
        }
    }




    public void onGuildJoin(GuildJoinEvent event) {

        // create our mysql database connection
        MySQL db = new MySQL();
        String guildID = event.getGuild().getId();
        List<Member> members = event.getGuild().getMembers();
        String query = null;

        String username = null;
        String userID = null;

        for (Member member : members) {
            username = member.getEffectiveName();
            userID = member.getUser().getId();
            query = "INSERT INTO users (guildID, username, userID) VALUES ('"+guildID+"', '"+username+"', '"+userID+"');";
            db.exQuery(query);
        }
        query = "INSERT INTO guilds (guildID, guildname) VALUES ('"+guildID+"', '"+event.getGuild().getName()+"');";
        db.exQuery(query);

    }

    public void onGuildLeave(GuildLeaveEvent event) {
        utils.MySQL db = new utils.MySQL();
        String guildID = event.getGuild().getId();
        db.exQuery("DELETE FROM users WHERE guildID='"+guildID+"';");
        db.exQuery("DELETE FROM guilds WHERE guildID='"+guildID+"';");
    }

}

