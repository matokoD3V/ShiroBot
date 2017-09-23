package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.MyAnimeList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GetAnime extends Command {
    public GetAnime() {
        this.name = "anime";
        this.help = "gets information on an anime from MAL";
    }

    @Override
    protected void execute(CommandEvent event) {
        String msg = event.getMessage().getContent();
        String target = msg.substring(8, msg.length());
        String url = getURL(target);

        Document d = null;
        try {
            d = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyAnimeList show = new MyAnimeList(d);
        EmbedBuilder embed = new EmbedBuilder();

        embed.setAuthor(show.getName(), url, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2UPzEO0GZIta2q3jSTZTK1L4ZSix5qihfHvetFXPnwyHV0WAd6w");
        embed.setThumbnail(show.getImg());
        embed.setDescription(show.getSynopsis());
        embed.addField("Episodes: ", show.getEpisodes(), true);
        event.getTextChannel().sendMessage(embed.build()).queue();
    }


    private String getURL(String name) {
        String url = null;
        try {
            url = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Document d = null;
        try {
            d = Jsoup.connect("https://myanimelist.net/search/all?q="  + url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int track = 0;
        Elements body=d.select("div.list.di-t.w100");
        String URL = null;
        for(Element ele:body) {
            if(track > 0)
                break;
            URL = ele.getElementsByTag("a").attr("href");
            track++;
        }
        return URL;
    }
}