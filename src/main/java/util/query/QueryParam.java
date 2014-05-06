package util.query;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.web.util.UriUtils;
import org.testng.Assert;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA. @9/24/12 5:50 PM
 * Author: Administrator
 * Copyright © 1994-2011. EMC Corporation. All Rights Reserved.
 */
public class QueryParam implements Comparable {

    public static final String CUSTOMER_ID = "customerid";

    private boolean isEncodingEnabled = true;


    public QueryParam(String name, Object... values) {
        this.name = name;
        for (Object value : values) {
            this.values.add(String.valueOf(value));
        }
    }

    public QueryParam(String name) {
        this.name = name;
    }

    public void setEncodingEnabled(boolean encodingEnabled) {
        this.isEncodingEnabled = encodingEnabled;
    }

    public String getName() {
        return name;
    }

    public void setDelimiter(String delimiter, boolean isEncoding) {
        try {
            this.delimiter = isEncoding ? getEncodedValue(delimiter) : delimiter;
        } catch (UnsupportedEncodingException e) {
            Assert.fail(String.format("encoding delimeter failed: \n%s", e.getMessage()));
        }

    }

    protected void addValue(Object... values) {
        for (Object value : values) {
            this.values.add(String.valueOf(value));
        }
    }

    public List<String> getValues() {
        return values;
    }

    /**
     * return the values as a joined String with "," as separator,
     * please override this method for parameters whose multiple values are not join by this rule.
     *
     * @return a joined value with ","
     */
    public String getQueryValue() {
        return joinValueEncoded(values, delimiter);
    }

    public String getQuery() {
        return String.format("%s=%s", name, getQueryValue());
    }

    // override to use with StringUtil.join in RestUri
    public String toString() {
        return getQuery();
    }

    private String name;
    private String delimiter;
    private List<String> values = new ArrayList<String>();

    private String joinValueEncoded(List<String> values, String separator) {
        Iterator<String> iterator = values.iterator();
        String first = iterator.next();
        try {
            if (!iterator.hasNext())
                return getEncodedValue(first);
            StringBuilder queryValue = new StringBuilder(getEncodedValue(first));
            while (iterator.hasNext()) {
                queryValue.append(separator).append(getEncodedValue(iterator.next()));
            }
            return queryValue.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;


    }

    private String getEncodedValue(String value) throws UnsupportedEncodingException {
        if (isEncodingEnabled) {
            return UriUtils.encodeQueryParam(value, "UTF-8");
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!getClass().isAssignableFrom(obj.getClass())) return false;
        QueryParam queryParam = (QueryParam) obj;
        EqualsBuilder equalsBuilder = new EqualsBuilder().append(name, queryParam.name);
        if (values.size() != queryParam.values.size()) return false;
        for (int i = 0; i < values.size(); i++) {
            equalsBuilder.append(values.get(i), queryParam.values.get(i));
        }
        return equalsBuilder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
        hashCodeBuilder.append(name);
        for (String value : values) {
            hashCodeBuilder.append(value);
        }
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public int compareTo(Object o) {
        return this.getQueryValue().compareToIgnoreCase(((QueryParam) o).getQueryValue());
    }
}
