package convertors.json;

import models.forms.CampaignForm;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;

/**
 * User: mark
 * Date: 14-5-9
 * Time: 下午3:00
 */

public class RestFormJsonMessageConverter extends FormHttpMessageConverter {

    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false; // disable the read function for the form converter
    }


    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (!CampaignForm.class.isAssignableFrom(clazz)) {
            return false;
        }
        return true;
    }

}
