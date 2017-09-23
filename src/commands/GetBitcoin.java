package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class GetBitcoin extends Command {
    public GetBitcoin() {
        this.name = "bitcoin";
        this.help = "gets usd to bitcoin conversion based off current values.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String message = event.getMessage().getContent();
        String target = message.substring(10, message.length());
        double value = Double.parseDouble(target);
        double conversion = (double)Math.round((value*0.00028) * 100000000d) / 100000000d;
        event.getTextChannel().sendMessage("`$"+value+"usd` --> `"+conversion+"btc`.").queue();
    }
}