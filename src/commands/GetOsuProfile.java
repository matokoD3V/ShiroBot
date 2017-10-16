package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;


import com.sethsutopia.utopiai.osu.OSU;
import com.sethsutopia.utopiai.osu.OSUPlayer;
import com.sethsutopia.utopiai.osu.OSU.OsuGameMode;
import com.sethsutopia.utopiai.restful.RestfulException;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.core.EmbedBuilder;
import utils.CountryCodes;

import java.text.DecimalFormat;

public class GetOsuProfile extends Command {
    public GetOsuProfile() {
        this.name = "osu";
        this.help = "gets osu profile of a specific user";
    }

    @Override
    protected void execute(CommandEvent event) {

        //Suck my dick, not removing key.
        String key = "319e7d936f399ab7663781d5bf19858fdc04b2a8";
        OSU osu = new OSU(key);
        DecimalFormat df2 = new DecimalFormat( "#,###,###,###"); //Formats whole number with ,
        DecimalFormat df3 = new DecimalFormat( "#,###,###,##0.00"); //Formats decimal number with ,
        CountryCodes cc = new CountryCodes();

        //EmojiParser.parseToUnicode(":"+player.getCountry().toLowerCase()+":"), player.getProfileUrl()

        String target = event.getMessage().getContent().substring(6);
        try {
            OSUPlayer player = osu.getUser(target, OsuGameMode.OSU);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Osu! Profile for "+player.getUsername(), player.getProfileUrl(), "https://upload.wikimedia.org/wikipedia/commons/d/d3/Osu%21Logo_%282015%29.png");
            embed.setColor(java.awt.Color.decode("#FF66AA"));

            embed.addField("Performance",
                    "**PP:** "+df3.format(player.getPPRaw())+" PP\n"+
                    "**Level:** "+String.format("%.2f",player.getLevel())+"\n"+
                    "**Rank:** #"+df2.format(player.getPPRank())+"\n"+
                    "**Country Rank:** #"+df2.format(player.getPPCountryRank())+"\t"+"\n", true);

            embed.addField("Accuracy",
                    "**Accuracy:** "+String.format("%.2f",(player.getAcc()*100)) +"%\n"+
                    "**300s:** "+df2.format(player.getCount300())+"\n"+
                    "**100s:** "+df2.format(player.getCount100())+"\n"+
                    "**50s:** "+df2.format(player.getCount50()), true);

            embed.addField("Map Ranks",
                    "**SS:** "+df2.format(player.getCountRankSS())+"\n"+
                    "**S:** "+df2.format(player.getCountRankS())+"\n"+
                    "**A:** "+df2.format(player.getCountRankA())+"\n", true);

            embed.addField("Information",
                    "**Country:** " + player.getCountry() +" "+ EmojiParser.parseToUnicode(":"+player.getCountry().toLowerCase()+":")+"\n"+
                            "**Total:** "+player.getTotalScorePretty()+"\n"+
                            "**Ranked:** "+player.getRankedScorePretty()+"\n"+
                            "**Play Count:** "+df2.format(player.getPlayCount()), true);

            embed.setThumbnail(player.getAvatarUrl());
            embed.setFooter("Download osu! | https://osu.ppy.sh/p/download", null);

            event.getTextChannel().sendMessage(embed.build()).queue();
        } catch (RestfulException e) {
            e.printStackTrace();
        }
    }
}