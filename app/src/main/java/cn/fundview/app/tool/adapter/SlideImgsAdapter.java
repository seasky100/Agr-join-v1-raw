package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.fragment.GuideFragment;
import cn.fundview.app.fragment.SlideImgFragment;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.DeviceConfig;
import cn.fundview.app.tool.bitmap.BitmapItem;
import cn.fundview.app.view.UploadListener;

/**
 * Created by Administrator on 2015/11/22 0022.
 *
 * 首页滚动图片 适配器
 */
public class SlideImgsAdapter extends FragmentPagerAdapter {


    private List<String> imgs;//图片的集合

    public SlideImgsAdapter(FragmentManager fm, List<String> imgs) {

        super(fm);
        this.imgs = imgs;
    }


    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new SlideImgFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMG_URL_KEY, imgs.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }
}
