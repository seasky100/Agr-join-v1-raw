package cn.fundview.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.fundview.R;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * Created by Administrator on 2015/10/13 0013.
 * <p/>
 * 滑动 fragment
 */
public class SlideImgFragment extends Fragment {

    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        url = bundle.getString(Constants.IMG_URL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ImageView imageView = new ImageView(this.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        XUtilsImageLoader xUtilsImageLoader = new XUtilsImageLoader(this.getContext(),R.mipmap.banner_default);
        xUtilsImageLoader.display(imageView, this.url);
        return imageView;
    }
}
