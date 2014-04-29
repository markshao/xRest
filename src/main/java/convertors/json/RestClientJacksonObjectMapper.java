package convertors.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.annotation.PostConstruct;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午9:02
 */

public class RestClientJacksonObjectMapper extends ObjectMapper {

    @PostConstruct
    public void setSerializationConfig() throws Exception {
        this.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

    }

    @PostConstruct
    public void setSerializationConfigForDates() throws Exception {
        configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public RestClientJacksonObjectMapper() {
        this.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        this.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}