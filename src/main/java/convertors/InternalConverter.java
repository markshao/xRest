package convertors;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午9:00
 */

public interface InternalConverter<S> {

    /**
     * to adapt to the Spring HttpMessageConverter mechanism
     *
     * @param clazz
     * @return
     */
    boolean supports(Class<S> clazz);
}
