package linhtruong.com.commons.managers;

import android.content.Context;
import android.os.Environment;
import android.util.SparseArray;
import linhtruong.com.commons.BuildConfig;
import linhtruong.com.commons.R;
import linhtruong.com.commons.helpers.MSTimeHelper;
import linhtruong.com.commons.utils.MSLogUtil;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/1/18 - 16:45.
 * @organization VED
 */
public class MSFileManager {
    public static final long CACHE_DIR_SIZE = 100 * 1024 * 1024;

    public static final String DIR_IMAGE = "image";
    public static final String DIR_CACHE = "cache";
    public static final String DIR_CACHE_JS = "cache_js";
    public static final String DIR_EMOJI = "emoji";

    public static final String FILE_NAME_IMG = "IMG";
    public static final String FILE_NAME_IMG_CROP = "IMG_CROP";
    public static final String FILE_NAME_IMG_QRCODE = "IMG_QRCODE";

    public static final int LRU_JS = 0;

    private final Context mCtx;

    private final AtomicReference<File> mWritableRoot;
    private final SparseArray<DiskLruCache> mLruCaches;

    public MSFileManager(Context context) {
        mCtx = context;
        mWritableRoot = new AtomicReference<>();
        mLruCaches = new SparseArray<>();
    }

    public static void deleteRecur(File fileOrDirectory) {
        deleteRecur(fileOrDirectory, null);
    }

    public static void deleteRecur(File fileOrDirectory, DeleteCallback callback) {
        if (fileOrDirectory == null || !fileOrDirectory.exists()) {
            return;
        }

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecur(child, callback);
            }
        }

        if (callback == null || callback.shouldDelete(fileOrDirectory)) {
            MSLogUtil.d("deleting %s", fileOrDirectory);
            fileOrDirectory.delete();
        }
    }

    public String writeToFile(File dir, String name, byte[] content) {
        String filePath = null;
        FileOutputStream fos = null;
        try {
            File file = new File(dir, name);
            fos = new FileOutputStream(file);
            fos.write(content);
            filePath = file.getAbsolutePath();
        } catch (IOException e) {
            MSLogUtil.e(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return filePath;
    }

    public String writeToFile(File dir, String name, InputStream is) {
        File file = new File(dir, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int n;
            while ((n = is.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            MSLogUtil.e(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return file.getAbsolutePath();
    }

    public File getImageDir() {
        return getAppInternalDir(DIR_IMAGE);
    }

    public File getImageDir(String subDir) {
        File imageDir = getImageDir();
        if (imageDir != null && imageDir.exists()) {
            File childDir = new File(imageDir, subDir);
            if (!childDir.exists()) {
                childDir.mkdir();
            }
            return childDir;
        }
        return null;
    }

    public File getCacheDir() {
        return getAppInternalDir(DIR_CACHE);
    }

    public File getInternalStorageCache() {
        return mCtx.getCacheDir();
    }

    public File getEmojiDir() {
        return getAppInternalDir(DIR_EMOJI);
    }

    /**
     * returns a writable directory that is owned by the app, no permission is required to access this directory
     *
     * @param subdir
     * @return
     */
    public File getAppInternalDir(String subdir) {
        File root = mWritableRoot.get();
        if (root == null) {
            initRoot();
            root = mWritableRoot.get();
        }

        if (root == null) {
            return null;
        }

        File dir = new File(root, subdir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    public String getNewImageFileName() {
        return getFileName(FILE_NAME_IMG);
    }

    public String getNewCropImageFileName() {
        return getFileName(FILE_NAME_IMG_CROP);
    }

    public String getNewQrCodeImageFileName() {
        return getFileName(FILE_NAME_IMG_QRCODE);
    }

    private String getFileName(String name) {
        String format = "%s_%s_%d";
        return String.format(format, mCtx.getString(R.string.ms_mershop).toUpperCase(), name,
                MSTimeHelper.millNowAbsolute());
    }

    public DiskLruCache getLruCache(int cacheType) {
        synchronized (mLruCaches) {
            DiskLruCache cache = mLruCaches.get(cacheType);
            if (cache == null) {
                switch (cacheType) {
                    case LRU_JS:
                        cache = DiskLruCache.create(FileSystem.SYSTEM, getAppInternalDir(DIR_CACHE_JS),
                                BuildConfig.VERSION_CODE, 1, CACHE_DIR_SIZE);
                        break;
                }

                if (cache == null) {
                    throw new UnsupportedOperationException("unsupported lru cache type: " + cacheType);
                }

                mLruCaches.put(cacheType, cache);
            }
            return cache;
        }
    }

    private void initRoot() {
        File root = mCtx.getExternalFilesDir(null);
        if (root == null || !root.exists()) {
            String externalRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
            String packageName = mCtx.getPackageName();
            String path = String.format("%s/Android/data/%s/files", externalRoot, packageName);
            root = new File(path);
            if (!root.exists()) {
                if (!root.mkdir()) {
                    // fallback to internal storage
                    root = mCtx.getCacheDir();
                }
            }
        }

        if (mWritableRoot.compareAndSet(null, root)) {
            MSLogUtil.d("app writable dir root: " + root);
        }
    }

    public interface DeleteCallback {
        boolean shouldDelete(File file);
    }
}
