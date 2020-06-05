package application.config;

import application.controller.CustomerController;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "spring.application.cloudant")
public class CloudantProperties {
    final private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private String protocol;
    private String username;
    private String password;
    private String host;
    private int port;
    private String database;
    private String sharedSecret;
    private Database cloudantDatabase;

    @PostConstruct
    public void configure() throws MalformedURLException {
        try {
            logger.info("Connecting to cloudant at: " + this.getProtocol() + "://" + this.getHost() + ":" + this.getPort());
            final CloudantClient cloudantClient = ClientBuilder.url(new URL(this.getProtocol() + "://" + this.getHost() + ":" + this.getPort()))
                    .username(this.getUsername())
                    .password(this.getPassword())
                    .build();

            this.cloudantDatabase = cloudantClient.database(this.getDatabase(), true);

            // create the design document if it doesn't exist
            if (!cloudantDatabase.contains("_design/username_search" + "Index")) {
                final Map<String, Object> names = new HashMap<String, Object>();
                names.put("index", "function(doc){index(\"usernames\", doc.username); }");

                final Map<String, Object> indexes = new HashMap<String, Object>();
                indexes.put("usernames", names);

                final Map<String, Object> view_ddoc = new HashMap<String, Object>();
                view_ddoc.put("_id", "_design/username_searchIndex");
                view_ddoc.put("indexes", indexes);

                cloudantDatabase.save(view_ddoc);
            }

        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
