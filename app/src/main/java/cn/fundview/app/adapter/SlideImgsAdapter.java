package cn.fundview.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import java.util.List;

import cn.fundview.app.view.UploadListener;

/**
 * Created by Administrator on 2015/11/22 0022.
 *
 * 首页滚动图片 适配器
 */
public class SlideImgsAdapter extends PagerAdapter{

    private List<String> imgs;
    private Context context;

    private UploadListener listener;
    public SlideImgsAdapter(Context context, List<String> imgs) {

        this.context = context;
        this.imgs = imgs;
    }
    @Override
    public int getCount() {
        return this.imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //super.instantiateItem(container, position);
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(imageView,imgs.get(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        //container.removeView((View)object);
    }
}
