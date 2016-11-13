import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import org.fnlp.nlp.cn.CNFactory;

import java.io.*;
import java.util.*;


/**
 * Created by nan on 13/11/16.
 */
public class BeautyWeiboExtraction {
    public static void main(String[] args) throws Exception {
        CNFactory factory = CNFactory.getInstance("models");
        factory.loadDict("models/beautywords/beauty_dict.txt",
                "models/beautywords/brand_dict.txt");

        Set<String> beautyWordsSet = getBeautyWordsSet();

        // connect to MongoDB
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        DB db = mongoClient.getDB( "Sina" );
        DBCollection coll = db.getCollection("Tweets");
        DBCursor cursor = coll.find();

        HashMap<String, String> beautyTweets = new HashMap<String, String>();
        HashMap<String, String> nonBeautyTweets = new HashMap<String, String>();
        try {
            while(cursor.hasNext()) {
                DBObject curDoc = cursor.next();
                String curContent = curDoc.get("Content").toString();
                HashMap<String, String> curResult = factory.ner(curContent);
                curResult.keySet().retainAll(beautyWordsSet);
                String curId = curDoc.get("_id").toString();
                if (curResult.size() != 0) {
                    beautyTweets.put(
                        curId,
                        curContent);
                } else {
                    nonBeautyTweets.put(
                        curId,
                        curContent);
                }
            }
        } finally {
            cursor.close();
        }
        // save results
        saveResults2Json(beautyTweets, "beautyTweets.json");
        saveResults2Json(nonBeautyTweets, "nonBeautyTweets.json");
    }

    private static void saveResults2Json(
            HashMap<String, String> tweets, String filename)
            throws IOException {
        Writer writer = new FileWriter(filename);
        Gson gson = new GsonBuilder().create();
        gson.toJson(tweets, writer);
        writer.close();
    }

    private static Set<String> getBeautyWordsSet()
            throws FileNotFoundException {
        String path = "models/beautywords/beauty_dict.txt";
        Scanner scanner = new Scanner(new FileInputStream(path), "utf-8");
        Set<String> beautyWordsSet = new HashSet<String>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if(line.length() > 0) {
                String[] s = line.split("\\s");
                beautyWordsSet.add(s[0]);
            }
        }
        return beautyWordsSet;
    }
}
