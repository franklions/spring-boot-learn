package elasticsearch.client.demo.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/9.
 */
public class TransportClientProperties {

    private static final String PROPERTIES_PREFIXED="elasticsearch.transport.client.";
    private static final String HOST_KEY="host";
    private static final String PORT_KEY = "port";
    private String host;
    private Integer port;

    public TransportClientProperties()  {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = TransportClientProperties.class.getClassLoader()
                    .getResourceAsStream("elasticsearch.properties");

            properties.load(in);
            this.host = properties.getProperty(PROPERTIES_PREFIXED+HOST_KEY);
            this.port = Integer.parseInt( properties.getProperty(PROPERTIES_PREFIXED+PORT_KEY));
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
