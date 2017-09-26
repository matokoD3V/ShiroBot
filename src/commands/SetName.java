package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.managers.GuildController;
import utils.Constants;

public class SetName extends Command {
    public SetName()
    {
        this.name = "setname";
        this.help = "sets the nickname of the bot";
        this.arguments = "<name>";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        Member[] members = event.getGuild().getMembers().toArray(new Member[0]);
        String msg = event.getMessage().getContent();
        GuildController guild_c = new GuildController(event.getGuild());
        String target = "";
        String oldName = "";
        for (int i = 0; i < members.length; i++) {
            if (members[i].getUser().getId().equals(Constants.BOT_ID)) {
                oldName = members[i].getEffectiveName();
                if (!msg.toLowerCase().equals("s_setname")) {
                    target = msg.substring(10, msg.length());
                    guild_c.setNickname(members[i], target).submit();
                    event.getTextChannel().sendMessage("`" + event.getMember().getEffectiveName() + "` changed my name from `" + oldName + "` to `" + target + "`.").queue();
                }

                else if (msg.toLowerCase().equals("s_setname")) {
                    guild_c.setNickname(members[i], "").submit();
                    event.getTextChannel().sendMessage("`" + event.getMember().getEffectiveName() + "` reset my name to `" + members[i].getEffectiveName() + "`.").queue();
                }
            }
        }
    }
}
