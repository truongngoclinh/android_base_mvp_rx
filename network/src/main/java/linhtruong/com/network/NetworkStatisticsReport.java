package linhtruong.com.network;

/**
 * Network statistic report
 *
 * @author linhtruong
 * @date 10/1/18 - 16:37.
 * @organization VED
 */
public class NetworkStatisticsReport {
    private static final int REPORT_THRESHOLD = 10;
    private static final String HTTP_LOCK = "http-lock";
    private static int httpRequest;
    private static long httpConsume;

    public static void httpStat(long timeCost) {
        int average = 0;
        synchronized (HTTP_LOCK) {
            httpRequest++;
            httpConsume = httpConsume + timeCost;

            if (httpRequest > REPORT_THRESHOLD) {
                average = (int) (httpConsume / httpRequest);

                httpRequest = 0;
                httpConsume = 0;
            }
        }
/*        if (average > 0) {
            CustomEvent ev = new CustomEvent("HTTP");
            ev.putCustomAttribute("time", average);
            Answers.getInstance().logCustom(ev);
        }*/
    }
}
