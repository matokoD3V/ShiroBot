package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/*
## Means when to stop in the loop to get value

// Example Show
32 English: The Future Diary
33 Synonyms: Mirai Nikki, Mirai Nikki (2011)
34 Japanese: 未来日記
35 Type: TV
36 Episodes: 26
37 Status: Finished Airing
38 Aired: Oct 9, 2011 to Apr 15, 2012
39 Premiered: Fall 2011
40 Broadcast: Sundays at 23:00 (JST)
41 Producers: Lantis, Kadokawa Shoten, Rakuonsha, Kadokawa Pictures Japan, KlockWorx, chara-ani.com, 12 Diary Holders, Dwango, Sakura Create
42 Licensors: Funimation
43 Studios: Asread
44 Source: Manga
45 Genres: Action, Psychological, Supernatural, Thriller, Mystery, Shounen
46 Duration: 23 min. per ep.
47 Rating: R+ - Mild Nudity
48 Score: 7.971 (scored by 511,218 users) 1 indicates a weighted score. Please note that 'Not yet aired' titles are excluded.
49 1 indicates a weighted score. Please note that 'Not yet aired' titles are excluded.
50 Ranked: #5732 2 based on the top anime page. Please note that 'Not yet aired' and 'R18+' titles are excluded.
51 2 based on the top anime page. Please note that 'Not yet aired' and 'R18+' titles are excluded.
52 Popularity: #9
53 Members: 825,941
54 Favorites: 22,196

 */

public class MAL {
    private String ranked, popularity, members, name, episodes, score, synopsis, genres, status, aired, type, imgURL;
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

        int track = 0;
        //Synopsis
        body = d.select("span");
        for(Element ele:body) {
            if(track == 39){
                synopsis = ele.text();
                break;
            }
            track++;
        }
        track = 0;


        /*
        In div.js-scrollfix-bottom
         */
        body = d.select("div");


        //Episodes
        for(Element ele:body) {
            if(track == 36){
                episodes = ele.text();
                break;
            }
            track++;
        }
        episodes = episodes.replace("Episodes: ", "");
        track = 0;

        //Genres
        for(Element ele:body) {
            if(track == 45){
                genres = ele.text();
                break;
            }
            track++;
        }
        genres = genres.replace("Genres: ", "");
        track = 0;

        //Status
        for(Element ele:body) {
            if(track == 37){
                status = ele.text();
                break;
            }
            track++;
        }
        status = status.replace("Status: ", "");
        track = 0;

        //Aired
        for(Element ele:body) {
            if(track == 38){
                aired = ele.text();
                break;
            }
            track++;
        }
        aired = aired.replace("Aired: ", "");
        track = 0;

        //Type
        for(Element ele:body) {
            if(track == 35){
                type = ele.text();
                break;
            }
            track++;
        }
        type = type.replace("Type: ", "");
        track = 0;
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

    public String getScore() {
        return score;
    }

    public String getGenres() {
        return genres;
    }

    public String getStatus() {
        return status;
    }

    public String getAired() {
        return aired;
    }

    public String getType() {
        return type;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getEpisodes() {
        return episodes;
    }
}