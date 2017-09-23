package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UrbanDictionary extends Command {
    public UrbanDictionary() {
        this.name = "urban";
        this.help = "gets definition, url, and example of a word on UrbanDictionary";
    }

    @Override
    protected void execute(CommandEvent event) {
        String target;
        String definition = "";
        String example = "";
        String contributor = "";
        String url = "";
        String word = "";

        String msg = event.getMessage().getContent();

        UrbanDictionary dictionary = new UrbanDictionary();
        target = msg.substring(8,msg.length());
        EmbedBuilder embed = new EmbedBuilder();
        try {
            definition = dictionary.getUMeaning(target);
            example = dictionary.getUExample(target);
            contributor = dictionary.getUContributor(target);
            url = dictionary.getUURL(target);
            word = dictionary.getUWord(target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        embed.setAuthor("Urban Dictionary", url, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQvkQc0gf4VU7lkIxyj92w8LhWbSRLO6YZkB2If-5fS-UxE8A3kCq9w-8");
        embed.setColor(java.awt.Color.RED);
        embed.addField("Word", word, false);
        embed.addField("Definition", definition, false);
        embed.addField("Example", example, false);
        embed.setFooter(contributor, null);
        event.getTextChannel().sendMessage(embed.build()).queue();
    }



    /*

    REWRITE BELOW INTO EXECUTE

     */
    private String getUMeaning(String phrase) throws IOException {
        String holder="";
        String[] aPhrase = phrase.split("\\s");
        for(int i = 0; i < aPhrase.length; i++) {
            if(i != aPhrase.length-1)
                holder += aPhrase[i] + "+";
            else
                holder += aPhrase[i];
        }

        int tracker = 0;
        String meaning = "";
        Document d= Jsoup.connect("http://www.urbandictionary.com/define.php?term="+holder).get();
        Elements body=d.select("div.meaning");
        for(Element ele:body) {
            if(tracker == 0)
                meaning = ele.text();
            tracker++;
        }
        if(meaning.contains("There aren't any definitions for") && meaning.contains(" yet. Can you define it?"))
            meaning = "That word is not defined.";
        return meaning;
    }

    private String getUExample(String phrase) throws IOException {
        String holder="";
        String[] aPhrase = phrase.split("\\s");
        for(int i = 0; i < aPhrase.length; i++) {
            if(i != aPhrase.length-1)
                holder += aPhrase[i] + "+";
            else
                holder += aPhrase[i];
        }

        int tracker = 0;
        String example = "";
        Document d=Jsoup.connect("http://www.urbandictionary.com/define.php?term="+holder).get();

        Elements body=d.select("div.example");
        for(Element ele:body){
            if(tracker == 0)
                example = ele.text();
        }
        return example;
    }

    private String getUContributor(String phrase) throws IOException {
        String holder="";
        String[] aPhrase = phrase.split("\\s");
        for(int i = 0; i < aPhrase.length; i++) {
            if(i != aPhrase.length-1)
                holder += aPhrase[i] + "+";
            else
                holder += aPhrase[i];
        }

        int tracker = 0;
        String contributer = "";
        Document d=Jsoup.connect("http://www.urbandictionary.com/define.php?term="+holder).get();

        Elements body=d.select("div.contributor");
        for(Element ele:body){
            if(tracker == 0)
                contributer = ele.text();
        }
        return contributer;
    }

    private String getUWord(String phrase) throws IOException {
        String holder="";
        String[] aPhrase = phrase.split("\\s");
        for(int i = 0; i < aPhrase.length; i++) {
            if(i != aPhrase.length-1)
                holder += aPhrase[i] + "+";
            else
                holder += aPhrase[i];
        }

        int tracker = 0;
        String word = "";
        Document d=Jsoup.connect("http://www.urbandictionary.com/define.php?term="+holder).get();

        Elements body=d.select("div.def-header");
        for(Element ele:body){
            if(tracker == 0)
                word = ele.text();
        }
        return word.replace("unknown", ""); //Removes "unknown"
    }

    private String getUURL(String phrase) throws IOException {
        String holder="";
        String[] aPhrase = phrase.split("\\s");
        for(int i = 0; i < aPhrase.length; i++) {
            if(i != aPhrase.length-1)
                holder += aPhrase[i] + "+";
            else
                holder += aPhrase[i];
        }
        String URL = "http://www.urbandictionary.com/define.php?term="+holder;

        return URL;
    }
}
