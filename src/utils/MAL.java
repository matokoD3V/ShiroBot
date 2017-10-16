package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MAL {
    private String ranked, popularity, members, name, score, imgURL;
    private Document d;

    public MAL(int ID) {
        try {
            d = Jsoup.connect("https://myanimelist.net/anime/"+ID).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setRanked();
    }

    private void setRanked() {
        //imgURL
        Elements body = d.select("div.js-scrollfix-bottom");
        for(Element ele:body) imgURL = ele.getElementsByTag("img").attr("src");


        //Ranked
        body = d.select("span.numbers.ranked");
        for(Element ele : body) ranked = ele.getElementsByTag("strong").text();


        //Popularity
        body = d.select("span.numbers.popularity");
        for(Element ele:body) popularity = ele.getElementsByTag("strong").text();


        //Members
        body = d.select("span.numbers.members");
        for(Element ele:body) members = ele.getElementsByTag("strong").text();


        //Score
        body = d.select("div.fl-l.score");
        for(Element ele:body) score = ele.text();


        //Name
        body = d.select("h1.h1");
        for(Element ele:body) name = ele.getElementsByTag("span").text();

    }

    public String getImg() {
        return imgURL;
    }

    public String getRanked() {
        return ranked;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public String getScore() { return score;}

}