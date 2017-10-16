package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sethsutopia.utopiai.myanimelist.Anime;
import com.sethsutopia.utopiai.myanimelist.MyAnimeList;
import com.sethsutopia.utopiai.restful.RestfulException;
import net.dv8tion.jda.core.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.MAL;
import utils.MySQL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GetAnime extends Command {
    public GetAnime() {
        this.name = "anime";
        this.help = "gets information on an anime from MAL";
        this.arguments = "<name of anime>";
    }

    @Override
    protected void execute(CommandEvent event) {
        MySQL db = new MySQL();
        if(db.getToggleInfo("anime", event.getGuild()) == 0) {
            return;
        }

        //Don't hack me... >:(
        MyAnimeList mal = new MyAnimeList("ShiroBot", "Inwardbend34382");
        EmbedBuilder embed = new EmbedBuilder();
        String target = event.getMessage().getContent().substring(8);

        try {
            Anime anime = mal.getAnime(target);
            MAL animeTwo = new MAL(anime.getId());



            if(!anime.getTitle().equals(anime.getEnglishTitle()))
                embed.setAuthor(anime.getTitle() + " ★ " + anime.getEnglishTitle(), "https://myanimelist.net/anime/"+anime.getId(), "https://myanimelist.cdn-dena.com/img/sp/icon/apple-touch-icon-256.png");
            else
                embed.setAuthor(anime.getTitle(), "https://myanimelist.net/anime/"+anime.getId(), "https://myanimelist.cdn-dena.com/img/sp/icon/apple-touch-icon-256.png");

            embed.setThumbnail(anime.getImageUrl());
            embed.setTitle("Synopsis");
            embed.setDescription(anime.getSynopsis());


            String endDate = "";
            if(anime.getEndDate() == null) { endDate = "Unknown"; }
            else { endDate = anime.getEndDate(); }

            embed.addField("Ranking Info",
                    "**Score:** " + anime.getScore() + "/10.00\n" +
                    "**Score Ranking:** " + animeTwo.getRanked()+"\n"+
                    "**Popularity Ranking:** "+animeTwo.getPopularity()+"\n"+
                    "**Members:** "+animeTwo.getMembers(),true);
            embed.addField("Information",
                    "**Type:** "+anime.getType()+"\n"+
                    "**Status:** "+anime.getStatus()+"\n"+
                    "**Episodes:** "+anime.getNumOfEpisodes()+"\n"+
                    "**Started:** "+anime.getStartDate()+"\n"+
                    "**Ended:** " + endDate,true);
            embed.setFooter("Uses Information from MyAnimeList.net", null);
            embed.setColor(java.awt.Color.decode("#1F98E7"));

            event.getTextChannel().sendMessage(embed.build()).queue();
        } catch (RestfulException e) {
            event.getTextChannel().sendMessage("I couldn't find that anime!").queue();
            e.printStackTrace();
        }
    }

}