package linhtruong.com.commons.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Log utilities
 *
 * @author linhtruong
 * @date 10/1/18 - 14:54.
 * @organization VED
 */
public class MSLogUtil {
    public static String DEFAULT_LOG_DIR = "mershop";

    /**
     * Configurable section
     */
    public static String CONFIG_APP_LOG_FLAG = "default";
    public static boolean CONFIG_NO_LOG = false;
    public static boolean CONFIG_GENERATE_TRACE = false;

    /**
     * Simple API to log into file
     * @param info log information
     */
/*    public static void f(Context context, String info){
        //check the permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            e("enable storage access to activate file logging");
            return;
        }
        //write the log file into the public cache
        //build a path
        String path = context.getExternalFilesDir(null) + File.separator + DEFAULT_LOG_DIR;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        String todayAsString =  DateFormat.format("yyyy-MM-dd-kk",c).toString();
        String currentTime = DateFormat.format("kk:mm:ss",c).toString();
        info = "\n-----start----- "+currentTime+" ------------\n" + info + "\n-----end----- "+currentTime+" ------------\n";
        byte[] data = info.getBytes();
        Storage.with(context).asyncWrite(path + File.separator + todayAsString , data, true, true);
    }*/



    public static void e(String format, Object... args) {
        if (!CONFIG_NO_LOG) {
            Log.e(CONFIG_APP_LOG_FLAG, getThreadName() + String.format(format, args));
        }

        String ss = String.format(format, args);
        if (ss.contains("UnknownFormatConversionException")) {
            i("UnknownFormatConversionException");
        }
    }

    public static void d(String format, Object... args) {
        if (!CONFIG_NO_LOG) {
            Log.d(CONFIG_APP_LOG_FLAG, getThreadName() + String.format(format, args));
        }

        String ss = String.format(format, args);
        if (ss.contains("UnknownFormatConversionException")) {
            i("OK");
        }
    }

    public static void i(String format, Object... args) {
        if (CONFIG_NO_LOG) {
            return;
        }
        if (!CONFIG_GENERATE_TRACE) {
            generateDebugTrace();
        }

        Log.i(CONFIG_APP_LOG_FLAG, String.format(format, args));
    }

    public static void e(Throwable e) {

        if (CONFIG_NO_LOG) {
            return;
        }

        StackTraceElement[] stackTraceElement = e.getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("e") == 0) {
                currentIndex = i + 1;
                break;
            }
        }

        if (currentIndex >= 0) {
            String fullClassName = stackTraceElement[currentIndex].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = stackTraceElement[currentIndex].getMethodName();
            String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());

            Log.e(CONFIG_APP_LOG_FLAG + " position", "at " + fullClassName + "." + methodName + "(" + className + ".java:" + lineNumber + ")");
        } else {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            Log.e(CONFIG_APP_LOG_FLAG, result.toString());
        }
    }

    private static void generateDebugTrace() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("i") == 0) {
                currentIndex = i + 1;
                break;
            }
        }

        if (currentIndex == -1) {
            Log.i(CONFIG_APP_LOG_FLAG, "CANNOT GENERATE DEBUG");
            return;
        }

        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());

        Log.i(CONFIG_APP_LOG_FLAG + " position", "at " + fullClassName + "." + methodName + "(" + className + ".java:" + lineNumber + ")");
    }

    private static String getThreadName() {
        return String.format("[thread_id:%d name=%s] ", Thread.currentThread().getId(),
                Thread.currentThread().getName());
    }
}
