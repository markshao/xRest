package util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriUtils;
import org.testng.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertEquals;

public class HttpUtil {
    public static final Log LOGGER = LogFactory.getLog(HttpUtil.class);

    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String META_DATA = "meta-data";
    public static final String BINARY = "binary";
    public static final String AUTHORIZATION = "Authorization";
    public static final String COOKIE = "Cookie";
    public static final String SETCOOKIE = "Set-cookie";
    public static final String MAXAGE = "Max-Age";
    public static final String EXPIRES = "Expires";
    public static final String PATH = "Path";
    public static final String HTTPONLY = "HttpOnly";
    public static final String BASIC = "Basic";

    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    public static final MediaType DOCUMENTUM_JSON = MediaType.valueOf("application/vnd.emc.documentum+json");
    public static final MediaType DOCUMENTUM_XML = MediaType.valueOf("application/vnd.emc.documentum+xml");
    public static final MediaType HOME_JSON = MediaType.valueOf("application/home+json");
    public static final MediaType HOME_XML = MediaType.valueOf("application/home+xml");
    public static final MediaType MULTIPART_MIXED = MediaType.valueOf("multipart/mixed");

    private static List<MediaType> supported_xml_media_list;
    private static List<MediaType> supported_json_media_list;

    public static boolean expectingJSON(HttpHeaders headers, String uri) {
        MediaType expected = getResponseType(headers, uri);
        return isRestJson(expected);
    }

    public static boolean expectingXML(HttpHeaders headers, String uri) {
        MediaType expected = getResponseType(headers, uri);
        return isRestXML(expected);
    }

    private static final String[] DATE_FORMATS = new String[]{
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "EEEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM d HH:mm:ss yyyy"
    };


    //TODO assume JSON as default representation; return application/vnd.emc.documentum.xml for all the xml
    public static MediaType getResponseType(MediaType accept, String uri) {
        // 2do, if the uri is invalid
        String resourceUri = StringUtils.substringBefore(uri, "?");
        String suffix = StringUtils.substringAfterLast(StringUtils.substringAfterLast(resourceUri, "/"), ".");
        // 2do, add suport for other suffix
        return suffix.equals("json") ||
                suffix.equals("") &&
                        (HttpUtil.DOCUMENTUM_JSON.equals(accept)
                                || MediaType.APPLICATION_JSON.equals(accept))
                ? HttpUtil.DOCUMENTUM_JSON : HttpUtil.DOCUMENTUM_XML;

    }

    public static String expectedSrcType(MediaType accept, String uri) {
        String resourceUri = StringUtils.substringBefore(uri, "?");
        String suffix = StringUtils.substringAfterLast(StringUtils.substringAfterLast(resourceUri, "/"), ".");
        return suffix.equals("json") || (!suffix.equals("xml") && !isRestXML(accept))
                ? HttpUtil.DOCUMENTUM_JSON.toString() : HttpUtil.DOCUMENTUM_XML.toString();
    }

    // TODO, add support for application/atom+xml media type
    public static MediaType getResponseType(HttpHeaders headers, String uri) {
        if (headers != null && headers.getAccept().size() != 0)
//            todo, sort the accepted media type
            return HttpUtil.getResponseType(headers.getAccept().get(0), uri);
        else {
            return HttpUtil.getResponseType((MediaType) null, uri);
        }
    }


    public static Class getDataType(MediaType contentType) {
        return null;
    }

    public static HttpHeaders setHttpHeaders(HttpHeaders requestHeaders, MediaType contentType,
                                             MediaType acceptMediaType) {
        if (requestHeaders == null)
            requestHeaders = new HttpHeaders();
        if (acceptMediaType != null)
            requestHeaders.setAccept(Arrays.asList(acceptMediaType));
        if (contentType != null)
            requestHeaders.setContentType(contentType);
        return requestHeaders;
    }

    //Assume the acceptMediaType is the same as contentType 
    public static HttpHeaders setHttpHeaders(MediaType acceptMediaType) {
        return setHttpHeaders(null, acceptMediaType, acceptMediaType);
    }

    public static HttpHeaders setHttpHeaders(MediaType contentType, MediaType acceptMediaType) {
        return setHttpHeaders(null, contentType, acceptMediaType);
    }

    public static String getFileNameFromContentDisposition(HttpHeaders responseHeaders) {
        Assert.assertNotNull(responseHeaders, "no headers!");
        List<String> contentDispositionHeader = responseHeaders.get(CONTENT_DISPOSITION);
        String fileAndName = StringUtils.substringAfter(contentDispositionHeader.get(0), ";");
        return StringUtils.substringAfter(fileAndName, "filename=");

    }

    public static String convertFileName(String fileName) {
        String[] searchList = "| \\ ? * < \" : > + [ ] /".split("\\s");
        String[] replaceList = new String[searchList.length];
        for (int i = 0; i < replaceList.length; i++) {
            replaceList[i] = "_";
        }
        return StringUtils.replaceEach(fileName, searchList, replaceList);
    }

    public static String getRFC1123DateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATS[0]);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    public static String getRFC850DateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATS[1]);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    public static String getAsctimeDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATS[2]);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    public static String getSuffixByMediaType(MediaType mediaType) {
        String dotXML = ".xml";
        String dotJSON = ".json";
        if (isRestJson(mediaType)) return dotJSON;
        if (isRestXML(mediaType)) return dotXML;
        return "";
    }

    public static List<MediaType> getSupportedXMLMediaType() {
        if (supported_xml_media_list == null) {
            supported_xml_media_list = new ArrayList<MediaType>();
            supported_xml_media_list.add(MediaType.APPLICATION_XML);
            supported_xml_media_list.add(MediaType.APPLICATION_ATOM_XML);
            supported_xml_media_list.add(HttpUtil.DOCUMENTUM_XML);
        }
        return supported_xml_media_list;
    }

    public static List<MediaType> getSupportedJSONMediaType() {
        if (supported_json_media_list == null) {
            supported_json_media_list = new ArrayList<MediaType>();
            supported_json_media_list.add(MediaType.APPLICATION_JSON);
            supported_json_media_list.add(HttpUtil.DOCUMENTUM_JSON);
        }
        return supported_json_media_list;
    }

    public static boolean isRestJson(MediaType mediaType) {
        if (mediaType == null)
            return false;
        for (MediaType supportedMediaType : getSupportedJSONMediaType()) {
            if (supportedMediaType.includes(mediaType))
                return true;
        }
        return false;
        //return HttpUtil.DOCUMENTUM_JSON.includes(mediaType) || MediaType.APPLICATION_JSON.includes(mediaType);
    }

    public static boolean isRestXML(MediaType mediaType) {
        if (mediaType == null)
            return false;
        for (MediaType supportedMediaType : getSupportedXMLMediaType()) {
            if (supportedMediaType.includes(mediaType))
                return true;
        }
        return false;
    }

    public static void checkSrcContentType(MediaType mediaType, String type) {
        if (isRestXML(mediaType)) {
            if (mediaType.equals(MediaType.APPLICATION_ATOM_XML))
                assertEquals(type, HttpUtil.DOCUMENTUM_XML.toString());
            else
                assertEquals(type, MediaType.APPLICATION_XML_VALUE.toString());
        } else {
            if (mediaType.equals(HttpUtil.DOCUMENTUM_JSON))
                assertEquals(type, HttpUtil.DOCUMENTUM_JSON.toString());
            else
                assertEquals(type, MediaType.APPLICATION_JSON_VALUE);
        }

    }


    public static String encode(String name) {
        String path = name;
        try {
            path = URLEncoder.encode(URLEncoder.encode(name, "UTF-8").replaceAll("\\.", "%2E"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Assert.fail(String.format("encoding %s name failed:\n%s", name, e.getMessage()));
        }
        return path;
    }

    public static String decode(String path) {
        String name = path;
        try {
            path = UriUtils.decode(UriUtils.decode(path, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Assert.fail(String.format("encoding %s name failed:\n%s", name, e.getMessage()));
        }
        return path;
    }

}
