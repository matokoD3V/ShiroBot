import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.commandclient.examples.AboutCommand;
import com.jagrosh.jdautilities.commandclient.examples.PingCommand;
import com.jagrosh.jdautilities.commandclient.examples.ShutdownCommand;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import java.awt.Color;
import java.io.IOException;
import javax.security.auth.login.LoginException;

import commands.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class ShiroBot {
    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException
    {
        // the first is the bot token
        String token = "MzU5MDg1MjE4ODM0OTM5OTA1.DKB3rg.pjE0eHMpv1T-5Qvt9FegoGFV7hI";

        // the second is the bot's owner's id
        String ownerId = "235995758896087041";

        // define an eventwaiter, dont forget to add this to the JDABuilder!
        EventWaiter waiter = new EventWaiter();

        // define a command client
        CommandClientBuilder client = new CommandClientBuilder();

        // The default is "Type !!help" (or whatver prefix you set)
        client.useDefaultGame();

        // sets the owner of the bot
        client.setOwnerId(ownerId);

        // sets the bot prefix
        client.setPrefix("s_");

        // adds commands
        client.addCommands(
                // command to show information about the bot
                new AboutCommand(Color.BLUE, "a retarded weeb bot.",
                        new String[]{"Niggers","Chicken","Watermelon"},
                        new Permission[]{Permission.ADMINISTRATOR}),

                // command to show a random cat
                //new CatCommand(),

                // command to make a random choice
                //new ChooseCommand(),

                // command to say hello
                //new HelloCommand(waiter),

                new SetName(),
                new SendBroadcast(),
                new GetGirl(),
                new JoinVoice(),
                new LeaveVoice(),

                new PingCommand(),

                new ShutdownCommand());

        // start getting a bot account set up
        new JDABuilder(AccountType.BOT)
                // set the token
                .setToken(token)

                // set the game for when the bot is loading
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.of("loading..."))

                // add the listeners
                .addEventListener(waiter)
                .addEventListener(client.build())

                // start it up!
                .buildAsync();

    }
}
