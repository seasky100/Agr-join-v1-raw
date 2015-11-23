package cn.fundview.app.view;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import java.io.File;

/**
 * 文件下载 监听器
 */
public interface DownloadListener {

    void start();

    void loading(long total, long current, boolean isUploading);

    void success(Object responseInfo);

    void failure(HttpException error, String msg);
}