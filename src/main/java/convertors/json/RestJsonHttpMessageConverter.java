package convertors.json;

import convertors.RestInternalConverter;
import convertors.util.InternalConverterUtil;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午8:58
 */

public class RestJsonHttpMessageConverter extends MappingJacksonHttpMessageConverter {
    private List<RestInternalConverter> jsonConverters = new ArrayList<RestInternalConverter>();

    public RestJsonHttpMessageConverter() {
        setObjectMapper(new RestClientJacksonObjectMapper());
    }

    public List<RestInternalConverter> getJsonConverters() {
        return jsonConverters;
    }

    public void setJsonConverters(List<RestInternalConverter> jsonConverters) {
        this.jsonConverters = jsonConverters;

        for (RestInternalConverter internalConverter : jsonConverters) {
            InternalConverterUtil.jsonConverters.add(internalConverter);
        }
    }

    public void addJsonInternalConverter(RestInternalConverter jsonConverter) {
        this.jsonConverters.add(jsonConverter);
        InternalConverterUtil.jsonConverters.add(jsonConverter);
    }

    @Override
    public Object read(Type type, Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return convertByRestFirst((Class) type, inputMessage);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return convertByRestFirst(clazz, inputMessage);
    }

    @Override
    protected void writeInternal(Object data, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(convertByRestFirst(data), outputMessage);
    }

    //* ===================================== Private Methods ===================================== *//

    private Object convertByRestFirst(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        for (RestInternalConverter converter : getJsonConverters()) {
            if (converter.supports(clazz)) {
                return converter.read(super.readInternal(HashMap.class, inputMessage), clazz);
            }
        }
        return super.readInternal(clazz, inputMessage);
    }

    private Object convertByRestFirst(Object data) throws IOException {
        // if converter found, a map will be returned
        for (RestInternalConverter converter : getJsonConverters()) {
            if (converter.supports(data.getClass())) {
                return converter.write(data);
            }
        }
        return data;
    }
}

