package db.model;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午9:03
 */

public interface IDbValidate {

    /**
     * Validate the model against the server response with database
     * THROW EXCEPTION?
     *
     * @throws Exception
     */
    public void validateSchema() throws Exception;
}
