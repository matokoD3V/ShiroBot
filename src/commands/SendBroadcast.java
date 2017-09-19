package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class SendBroadcast extends Command {
    public SendBroadcast()
    {
        this.name = "broadcast";
        this.help = "sends a message to all discord servers.";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        Message msg = event.getMessage();
        Guild[] guilds = event.getJDA().getGuilds().toArray(new Guild[0]);
        String broadcast = msg.getContent().substring(12, msg.getContent().length());
        TextChannel[] channel = null;

        for(int i = 0; i < guilds.length; i++)
        {
            channel = guilds[i].getTextChannels().toArray(new TextChannel[0]);
            channel[0].sendMessage("Broadcast: `" + broadcast + "`").queue();
        }

    }
}