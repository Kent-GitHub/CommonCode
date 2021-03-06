package cn.kl.utils.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * * Created by Kent_Lee on 2017/7/1.
 */

public class LogFileUtils {
    private String TAG = "LogFileUtils";
    private static boolean isLogFileEnable = false;

    private boolean initialized;

    private String LOG_PATH = Environment.getExternalStorageDirectory() + "/cache/";

    private String fileName;

    private float maxCacheSize = 1;

    private boolean createDirAuto;

    private File logFile;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ::: ");

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat fileNameSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressLint("SimpleDateFormat")
    private LogFileUtils() {

    }

    private static final class HOLDER {
        private static final LogFileUtils INSTANCE = new LogFileUtils();
    }

    public static LogFileUtils getInstance() {
        return HOLDER.INSTANCE;
    }

    public void init(Context context) {
        init(LogFileConfig.defaultConfig(context));
    }

    public void init(LogFileConfig config) {
        setConfig(config);
        initialized = true;
        writeInitInfo();
    }

    private void writeInitInfo() {
        long realTime = SystemClock.elapsedRealtime();
        int day_u = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        writeToFile("LogFileUtils_hashCode: " + this.toString() + ", elapsedRealTime: " + realTime + ", 系统启动时间: " + (realTime / day_u + "天" + sdf.format(new Date(realTime))));
    }

    public static void setLogFileEnable(boolean logFileEnable) {
        isLogFileEnable = logFileEnable;
    }

    public void writeToFile(Exception e) {
        writeToFile(e, false);
    }

    public void writeToFile(Exception e, boolean logcat) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        writeToFile(sw.toString(), logcat);
    }

    public void writeToFile(String tag, Exception e) {
        writeToFile(tag, e, false);
    }

    public void writeToFile(String tag, Exception e, boolean logcat) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        writeToFile(tag, sw.toString(), logcat);
    }

    public void writeToFile(String msg) {
        writeToFile(msg, false);
    }

    public void writeToFile(String msg, boolean logcat) {
        writeToFile(null, msg, logcat);
    }

    public void writeToFile(String tag, String msg) {
        writeToFile(tag, msg, true);
    }

    public void writeToFile(String tag, String msg, boolean logcat) {
        if (logcat) {
            if (msg.contains("Exception")) {
                Log.e(tag != null ? tag : TAG, msg);
            } else {
                Log.i(tag != null ? tag : TAG, msg);
            }
        }
        if (!isLogFileEnable || !initialized) return;
        if (tag == null) {
            tag = "";
        }
        Date time = new Date();
        if (logFile == null) {
            logFile = new File(LOG_PATH + fileName + "_" + tag + ".txt");
        }
        try {
            if (createDirAuto) {
                logFile.getParentFile().mkdirs();
            }
            if (!logFile.getParentFile().exists()) return;
            String timeStr = sdf.format(time);
            if (!logFile.exists()) {
                logFile.createNewFile();
                writeInitInfo();
            }
            boolean append = true;
            if (logFile.length() > maxCacheSize * 1024 * 1024) {
                append = false;
            }
            FileWriter writer = new FileWriter(logFile, append);
            writer.write(timeStr);
            writer.write(msg + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.w(TAG, "writeToFile: ", e);
        }
    }

    public void setConfig(LogFileConfig config) {
        LOG_PATH = Environment.getExternalStorageDirectory() + "/cache/" + config.parentPath + File.separator;
        fileName = config.fileName;
        maxCacheSize = config.maxSize;
        isLogFileEnable = config.logFileEnable;
        createDirAuto = config.createDirAuto;
        TAG = TextUtils.isEmpty(config.tag) ? TAG : config.tag;
    }

}
