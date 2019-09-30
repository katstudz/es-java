package by.kraskouski.elasticsearch.api;


import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@SuppressWarnings("deprecation")
public class AddElem {

    private RestHighLevelClient client;

    public AddElem(){
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        try {
            init();
            init();
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSome(){


    }

    void init() throws IOException { //user1: kimchy; user2: Victor
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "Victor");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("blog", "posts")
                .id(UUID.randomUUID().toString()).source(jsonMap);
        IndexResponse indexResponse = client.index(indexRequest);

        System.out.println(indexResponse.getResult());

    }


}
