package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.core.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetGirl extends Command {
    public GetGirl() {
        this.name = "girl";
        this.help = "gets anime girl";
    }

    @Override
    protected void execute(CommandEvent event)
    {

        //Gets random anime girl picture.

        Document d= null;
        try {
            d = Jsoup.connect("http://weheartit.com/pictures/cute%20anime%20girl?landing=true&page=3&before=297357460").get();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> imgs = new ArrayList<String>();
        int track = 0;

        Elements body=d.select("div.entry.grid-item");
        for(Element ele:body) {
            imgs.add(track, ele.getElementsByTag("img").attr("src"));
            track++;
        }

        //Image with embed.
        EmbedBuilder embed = new EmbedBuilder();
        embed.setImage(imgs.get(ThreadLocalRandom.current().nextInt(0, imgs.size() + 1)));
        event.getTextChannel().sendMessage(embed.build()).queue();

        //Image without embed.
        //event.getTextChannel().sendMessage(imgs.get(ThreadLocalRandom.current().nextInt(0, imgs.size() + 1))).queue();
    }
}
