package utils;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Listener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMember().getUser().isBot())
            return;


        MySQL db = new MySQL();
        ResultSet memberinfo = db.getMemberInfo(event.getMember().getUser().getId(), event.getGuild());
        int memberExp = 0;
        int memberOrigLevel = 0;
        int memberLevel = 0;
        int requiredExp = 0;
        try {
            while (memberinfo.next() == true)
            {
                memberLevel = memberinfo.getInt("userLevel");
                memberExp = memberinfo.getInt("userExp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        memberOrigLevel = memberLevel;
        memberExp += 1;
        requiredExp = memberLevel * 20;
        while(memberExp > requiredExp) {
            memberLevel++;
            requiredExp += memberLevel * 20;
        }

        if(memberOrigLevel < memberLevel) {
            event.getTextChannel().sendMessage("`" + event.getMember().getEffectiveName()+"` has leveled up to level `" + memberLevel + "`!").queue();
        }

        db.exQuery("UPDATE users SET userLevel="+memberLevel+", userExp="+memberExp+" WHERE userID='"+event.getMember().getUser().getId()+"' AND guildID='"+event.getGuild().getId()+"';");
    }




    public void onGuildJoin(GuildJoinEvent event) {
        //MOVE THIS TO OWN SQL CLASS

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
    }

    public void onGuildLeave(GuildLeaveEvent event) {
        utils.MySQL db = new utils.MySQL();
        String guildID = event.getGuild().getId();
        String query = "DELETE FROM users WHERE guildID='"+guildID+"';";
        db.exQuery(query);
    }

}

