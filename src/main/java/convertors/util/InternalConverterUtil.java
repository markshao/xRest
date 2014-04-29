package convertors.util;

import convertors.RestInternalConverter;
import convertors.json.RestClientJacksonObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午9:05
 */

public class InternalConverterUtil {
    public static List<RestInternalConverter> jsonConverters = new ArrayList<RestInternalConverter>();
    private static ObjectMapper mapper = new RestClientJacksonObjectMapper();

    static {

//        jsonConverters.add(new FeedJsonConverter());


    }

    public static Object nextObject(Map entry, Class entryType) {
        for (RestInternalConverter converter : jsonConverters) {
            if (converter.supports(entryType)) {
                return converter.read(entry, entryType);
            }
        }
        return mapper.convertValue(entry, entryType);

    }

    public static Map nextJSON(Object data) {
        for (RestInternalConverter converter : jsonConverters) {
            if (converter.supports(data.getClass())) {
                return (Map) converter.write(data);
            }

        }
        return mapper.convertValue(data, HashMap.class);
    }
}
