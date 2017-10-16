package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class SetNotifierChannel extends Command {
    public SetNotifierChannel()
    {
        this.name = "osuchannel";
        this.help = "sets the text channel that osu notifications are sent to";
        this.arguments = "<text channel>";
    }

    @Override
    protected void execute(CommandEvent event)
    {

    }
}
