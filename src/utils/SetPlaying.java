package utils;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SetPlaying extends ListenerAdapter {

    static Random r = new Random();
    static Timer timer = new Timer();
    static String x;

    String[] status = {

            "with fire",
            "osu!",
            "s_help",
            "owo",
            " ",
            "xD",
            "with rape",
            "uwu",
            "cx",
            "name jeff",
            "you",
            "World of Warcraft",
            "Runescape 3",
            "Counter-Strike: Source",
            "@everyone"};

    public void onReady(ReadyEvent e) { //changes the "Playing" status message with 5 minute intervals.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                x = status[r.nextInt(status.length)];
                e.getJDA().getPresence().setGame(Game.of(x));
            }
        }, 10*1000, 5*60*1000);
    }

}