package convertors;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午8:59
 */
public interface RestInternalConverter<T, S> extends InternalConverter<S> {

    public S read(T data, Class<S> feedType);

    public T write(S object);
}

