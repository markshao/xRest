package util;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriUtils;
import org.testng.Assert;
import util.query.QueryParam;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by IntelliJ IDEA. @9/24/12 4:34 PM
 * Author: Administrator
 * Copyright © 1994-2011. EMC Corporation. All Rights Reserved.
 */
public class RestUri {
    private String suffix = "";

    public static final String jsonSuffix = ".json";
    public static final String xmlSuffix = ".xml";

    private Map<String, String> queryParams = new LinkedHashMap<String, String>();


    public RestUri suffix(MediaType mediaType) {
        this.suffix = HttpUtil.getSuffixByMediaType(mediaType);
        return this;
    }

    public RestUri suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public RestUri setQueryParam(QueryParam... params) {
        for (QueryParam param : params)
            this.queryParams.put(param.getName(), param.getQuery());
        return this;
    }

    public RestUri setQueryParam(String name, String... values) {
        QueryParam param = new QueryParam(name, values);
        this.queryParams.put(param.getName(), param.getQuery());
        return this;
    }

    /*============================================ static methods from RestUri ============================================*/

    public static String getSuffix(MediaType mediaType) {
        return HttpUtil.isRestXML(mediaType) ? xmlSuffix : jsonSuffix;
    }

    public static String appendSuffix(String url, MediaType mediaType) {
        StringBuilder result = new StringBuilder();
        if (url.contains("?")) {
            result.append(url.substring(0, url.indexOf("?")));
            result.append(getSuffix(mediaType));
            result.append(url.substring(url.indexOf("?"), url.length()));
        } else {
            result.append(url);
            result.append(getSuffix(mediaType));
        }
        return result.toString();

    }

    public static String appendQueryParam(String uri, QueryParam... params) {

        String withoutQuery = StringUtils.substringBefore(uri, "?");

        Map<String, String> queryMap = new LinkedHashMap<String, String>();

        List<QueryParam> paramsFromUri = parseQueryParams(uri);
        for (QueryParam queryParam : paramsFromUri) {
            queryMap.put(queryParam.getName(), queryParam.getQueryValue());
        }

        for (QueryParam param : params) {
            queryMap.put(param.getName(), param.getQueryValue());
        }

        StringBuilder query = new StringBuilder();
        for (Map.Entry entry : queryMap.entrySet()) {
            if (query.length() != 0) query.append("&");
            query.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return String.format("%s?%s", withoutQuery, query.toString());
    }


    /**
     * append query parameters, if uri contains the param(s), param(s) will not be overwritten
     */
    public static String appendQueryParam2(String uri, QueryParam... params) {
        // here, we used the toString method of the QueryParam
        if (params.length == 0) return uri;
        String query = StringUtils.join(params, '&');
        return uri.contains("?") ?
                String.format("%s&%s", uri, query) : String.format("%s?%s", uri, query);
    }

    /**
     * pase the uri to a list of query params, and the value will remains in the encoded format
     *
     * @param uri
     * @return
     */
    public static List<QueryParam> parseQueryParams(String uri) {

        String[] queries = StringUtils.substringAfter(uri, "?").split("&");
        List<QueryParam> params = Collections.emptyList();
        if (!queries[0].equals("")) {
            params = new ArrayList<QueryParam>();
            for (String query : queries) {
                String name = StringUtils.substringBefore(query, "=");
                String value = StringUtils.substringAfter(query, "=");
                String[] values = value.split(",");

                for (int i = 0; i < values.length; i++) {
                    try {
                        values[i] = UriUtils.decode(values[i], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Assert.fail(e.getMessage());
                    }
                }
                QueryParam queryParam = new QueryParam(name, values);
                queryParam.setDelimiter(",", false);
                params.add(queryParam);

            }
        }
        return params;
    }

    public static void compareLinks(String link1, String link2) {

        Assert.assertEquals(StringUtils.substringBefore(link1, "?"), StringUtils.substringBefore(link2, "?"));
        List<QueryParam> params1 = parseQueryParams(link1);
        List<QueryParam> params2 = parseQueryParams(link2);
        Assert.assertEquals(params1.size(), params2.size(), "Query parameters' count compare fail.");
        Collections.sort(params1);
        Collections.sort(params2);
        for (int i = 0; i < params1.size(); i++) {
            Assert.assertEquals(params1.get(i).getName().compareToIgnoreCase(params2.get(i).getName()), 0,
                    String.format("Query name compare fail:\nactual<%s>\nexptected<%s>", params1.get(i).getName(), params2.get(i).getName()));

            List<String> values1 = params1.get(i).getValues();
            List<String> values2 = params2.get(i).getValues();
            Assert.assertEquals(values1.size(), values2.size(),
                    String.format("Query<%s> values' count compare fail:\nactual<%s>\nexptected<%s>", params1.get(i).getName(), values1, values2));
            Assert.assertTrue(values1.containsAll(values2),
                    String.format("Query<%s> values compare fail:\nactual<%s>\nexptected<%s>", params1.get(i).getName(), values1, values2));
        }
    }
}
