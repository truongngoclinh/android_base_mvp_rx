package linhtruong.com.mershop.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import linhtruong.com.commons.utils.MSLogUtil;

/**
 * Base app
 *
 * @author linhtruong
 * @date 10/1/18 - 11:40.
 * @organization VED
 */
public class BaseApplication extends Application {
    private volatile static BaseComponent mComponent;
    private String mProcessName;

    @Override
    public void onCreate() {
        super.onCreate();
        mProcessName = getCurrentProcess();
    }

    protected void setComponent(BaseComponent component) {
        mComponent = component;
    }

    protected static BaseComponent getComponent() {
        return mComponent;
    }

    protected boolean isMainProcess() {
        return TextUtils.isEmpty(mProcessName);
    }

    protected String getCurrentProcess() {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                MSLogUtil.d("loop %d %s", processInfo.pid, processInfo.processName);
                int index = processInfo.processName.indexOf(':');
                if (index > 0) {
                    currentProcessName = processInfo.processName.substring(index);
                }
                break;
            }
        }
        MSLogUtil.d("current process %s", currentProcessName);
        return currentProcessName;
    }
}
