package compaign;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午2:57
 */

import base.BaseTestClass;
import org.testng.annotations.Test;
import util.RestUri;
import util.query.QCustomerId;

@Test
public class TestCompaignNormal extends BaseTestClass {

    public void testHttpClient() throws Exception {
        System.out.println(RestUri.appendQueryParam("http://baidu.com/", new QCustomerId("1111")));
    }
}
