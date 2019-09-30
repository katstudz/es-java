package by.kraskouski.elasticsearch;

import by.kraskouski.elasticsearch.api.AggregationApi;
import by.kraskouski.elasticsearch.api.GetApi;
import by.kraskouski.elasticsearch.api.IndexApi;
import lombok.var;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * General bootstrap class.
 */
public class Application {

    private static final RestHighLevelClient CLIENT;
    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String INDEX = "my-index";
    private static final String TYPE = "my-type";

    static {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "changeme"));



        RestClientBuilder builder = RestClient.builder(new HttpHost(HOST, PORT, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        CLIENT = new RestHighLevelClient(builder);
    }

    public static void main(final String... args) throws IOException {
        try {
//            new IndexApi(CLIENT, INDEX, TYPE).exampleWithJsonRequest();
//            new GetApi(CLIENT, INDEX, TYPE).getRequest();
//            new GetApi(CLIENT, INDEX, TYPE).getRequestInvalidIndex();
            new AggregationApi(CLIENT, INDEX, TYPE).metricsMaxAggregation();
        } finally {
            CLIENT.close();
        }
    }

    public static Header[] prepareAuthHeader() {
        return new BasicHeader[]{};
    }
}
