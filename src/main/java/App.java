/**
 * Created by nan on 12/11/16.
 */
import org.fnlp.nlp.cn.CNFactory;

import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {

        // 创建中文处理工厂对象，并使用“models”目录下的模型文件初始化
        CNFactory factory = CNFactory.getInstance("models");
        factory.loadDict("models/beautywords/beauty_dict.txt",
                "models/beautywords/brand_dict.txt");

        String query = "我今天用nuke粉饼和兰蔻眼影上妆。";

        System.out.printf("query: %s\n", query);
        long startTime = System.nanoTime();
        HashMap<String, String> result = factory.ner(query);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.printf("NER cost: %d ms\n", duration/1000);

        startTime = System.nanoTime();
        String[] words = factory.seg(query);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.printf("SEG cost: %d ms\n", duration/1000);

        System.out.println("分词结果");
        for(String word : words) {
            System.out.print(word + " ");
        }

        System.out.println("");
        System.out.println("NER结果");
        for (String s: result.keySet()) {
            System.out.printf("%s = %s\n", s, result.get(s));
        }

    }
}
