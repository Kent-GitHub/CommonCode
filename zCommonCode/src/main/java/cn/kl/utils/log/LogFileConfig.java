package cn.kl.utils.log;

import android.content.Context;

public class LogFileConfig {
    /**
     * 父目录
     */
    String parentPath;
    /**
     * 日志文件名
     */
    String fileName;
    /**
     * 日志文件最大大小
     */
    float maxSize = 1;
    /**
     * enable
     */
    boolean logFileEnable = true;
    /**
     * 自动创建父目录
     * 父目录不存在时不输出日志
     */
    boolean createDirAuto = true;
    /**
     * logcat tag
     */
    String tag = "";

    private LogFileConfig() {
    }

    public static class Builder {
        String parentPath;
        String fileName;
        float maxSize = 1;
        boolean logFileEnable = true;
        boolean createDirAuto = true;
        String tag = "";

        public Builder(String fileName) {
            this(fileName, fileName);
        }

        public Builder(String parentPath, String fileName) {
            setFileName(fileName);
            setParentPath(parentPath);
        }

        public Builder setParentPath(String parentPath) {
            this.parentPath = parentPath;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setMaxFileSize(float maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setLogFileEnable(boolean enable) {
            this.logFileEnable = enable;
            return this;
        }

        public Builder setCreateDirAuto(boolean createDirAuto) {
            this.createDirAuto = createDirAuto;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public LogFileConfig build() {
            LogFileConfig config = new LogFileConfig();
            config.parentPath = parentPath;
            config.fileName = fileName;
            config.maxSize = maxSize;
            config.logFileEnable = logFileEnable;
            config.createDirAuto = createDirAuto;
            config.tag = tag;
            return config;
        }
    }


    public static LogFileConfig defaultConfig(Context context) {
        LogFileConfig config = new LogFileConfig();
        config.parentPath = context.getPackageName();
        config.fileName = context.getPackageName();
        config.maxSize = 1f;
        config.logFileEnable = true;
        config.createDirAuto = true;
        config.tag = "";
        return config;
    }
}
