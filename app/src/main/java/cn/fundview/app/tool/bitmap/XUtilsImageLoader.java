package cn.fundview.app.tool.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import cn.fundview.R;

/**
 * 项目名称：Agr-join-v1-raw
 * 类描述： xutils 框架的图片加载器
 * 创建人：lict
 * 创建时间：2015/11/23 0023 下午 3:23
 * 修改人：lict
 * 修改时间：2015/11/23 0023 下午 3:23
 * 修改备注：
 */
public class XUtilsImageLoader {

    private BitmapUtils bitmapUtils;
    private Context mContext;
    private ColorDrawable TRANSPARENT_DRAWABLE;

    public XUtilsImageLoader(Context context, int defaultImgId) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.configDefaultLoadingImage(defaultImgId);//默认背景图片
//        bitmapUtils.configDefaultLoadFailedImage(R.drawable.logo_new);//加载失败图片
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//设置图片压缩类型
        TRANSPARENT_DRAWABLE = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));

    }
    /**
     *
     * @author sunglasses
     * @category 图片回调函数
     */
    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {

        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
        }

        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            // super.onLoadCompleted(container, uri, bitmap, config, from);
            fadeInDisplay(container, bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri,
                                 Drawable drawable) {
            // TODO Auto-generated method stub
        }
    }


    /**
     * @author sunglasses
     * @category 图片加载效果
     * @param imageView
     * @param bitmap
     */
    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {//目前流行的渐变效果
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[] { TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap) });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }
    public void display(ImageView container,String url){//外部接口函数
        bitmapUtils.display(container, url,new CustomBitmapLoadCallBack());
    }
}
