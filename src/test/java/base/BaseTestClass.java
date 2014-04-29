package base;

import client.IRestClient;
import client.RestClientFactory;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 14-4-27
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */

public abstract class BaseTestClass {

    protected IRestClient m_restClient = RestClientFactory.getInstance().getRestClient();

}
