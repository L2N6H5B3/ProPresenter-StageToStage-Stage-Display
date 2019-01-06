package stageonjava.util;


/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */


public class ThreadUtil {
    public static void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
