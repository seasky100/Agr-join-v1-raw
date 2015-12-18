package cn.fundview.app.tool.bitmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;

import cn.fundview.R;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.DeviceConfig;
import cn.fundview.app.view.DownloadListener;

/**
 * bitmap item
 */
public class BitmapItem implements DownloadListener{

    private Context context;
    private ImageView imageView;
    private String url;
    private String destPath;                //图片目标路径
    private int defaultImgId;             // 图片下载失败/正在下载/不存在的时候显示的图片

    private Bitmap temp;                    //
    public BitmapItem(Context context, ImageView imageView, String url, String destPath, int defaultImgId) {

        this.context = context;
        this.imageView = imageView;
        this.url = url;
        this.destPath = DeviceConfig.getSysPath(context) + (destPath==null?"":destPath);
        this.defaultImgId = defaultImgId;
        Log.d("aaaaaaaaaaaaaaaaaaaa", destPath);
        Log.d("aaaaaaaaaaaaaaaaaaaa", url);
    }

    public void show() {

        if(destPath != null && destPath.trim() != "") {

            File file = new File(destPath);
            if(file.exists()) {

                temp = BitmapFactory.decodeFile(destPath);
                imageView.setImageBitmap(temp);
                imageView.setTag(null);
            }else {

                //下载图片到本地然后显示
                RService.downloadImgToImageView(url, destPath, this);
            }
        }else {

            //异步读流,不存本地
            RService.downloadImgToImageView(url,  DeviceConfig.getSysPath(context) + Constants.TEMP_PATH + url.substring(url.lastIndexOf("/") + 1), this);
        }


    }

    @Override
    public void start() {

        LogUtils.d("start...");
        temp = BitmapFactory.decodeResource(context.getResources(),defaultImgId);
        this.imageView.setImageBitmap(temp);
        imageView.setTag("");
    }

    @Override
    public void loading(long total, long current, boolean isUploading) {

        LogUtils.d("total = " + total + "   current = " + current);
    }

    @Override
    public void success(Object object) {

        LogUtils.d("success...");
        Log.d(Constants.TAG, object.getClass().getName());
        if(temp != null && !temp.isRecycled() ) {

            temp.recycle();
        }
        if(object instanceof File) {

            LogUtils.d("fileloading   filePath = " + ((File) object).getAbsolutePath());
            temp = BitmapFactory.decodeFile(((File) object).getPath());
        }
        this.imageView.setImageBitmap(temp);
        imageView.setTag(null);
    }

    @Override
    public void failure(HttpException error, String msg) {

        LogUtils.e("error msg : " + msg);
        if(temp == null ) {

            temp = BitmapFactory.decodeResource(context.getResources(), defaultImgId);
            this.imageView.setImageBitmap(temp);
        }
    }
}
