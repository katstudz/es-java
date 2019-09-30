
package by.kraskouski.elasticsearch.api;

import by.kraskouski.elasticsearch.Application;
import by.kraskouski.elasticsearch.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * Samples of using elasticsearch index API requests.
 */
public class AggregationApi {

    private final RestHighLevelClient client;
    private final String index;
    private final String type;

    public AggregationApi(final RestHighLevelClient client, final String index, final String type) {
        this.client = client;
        this.index = index;
        this.type = type;
    }

    public void metricsMaxAggregation() throws IOException {
//        initDatabase(); todo uncomment if first fetch app
        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.max("age_aggr").field("age"));
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        final SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = client.search(searchRequest, Application.prepareAuthHeader());
        final double result = ((Max) searchResponse.getAggregations().get("age_aggr")).getValue();
        System.out.println("Max age from documents: " + result);
    }

    private void initDatabase() throws IOException {
        User userKM = User.builder()
                .id(1L).age(10).firstname("Karol").lastname("M")
                .build();

        User userWW = User.builder()
                .id(1L).age(66).firstname("Woj").lastname("W")
                .build();

        final BulkRequest request = new BulkRequest();
        ObjectMapper oMapper = new ObjectMapper();

        byte[] bytes1 =  oMapper.writeValueAsBytes(userKM);
        request.add(new IndexRequest("email_subscription", "data",
                userKM.getId().toString()).source(bytes1, XContentType.JSON));

        byte[] bytes2 =  oMapper.writeValueAsBytes(userWW);
        request.add(new IndexRequest("email_subscription", "data",
                userKM.getId().toString()).source(bytes2, XContentType.JSON));
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        client.bulk(request, Application.prepareAuthHeader());
    }
}