package context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午8:40
 */

public class SpringContextUtil {

    private static ApplicationContext INSTANCE_JAVA;

    private SpringContextUtil() {
    }


    public static ApplicationContext getApplicationContextJava() {
        synchronized (SpringContextUtil.class) {
            if (INSTANCE_JAVA == null)
                INSTANCE_JAVA = new AnnotationConfigApplicationContext(ClientSpringConfiguration.class);
            return INSTANCE_JAVA;
        }
    }
}