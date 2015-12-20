package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.domain.model.Achv;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * 成果适配器
 */
public class AchvListAdapter extends BaseAdapter {

    private List<Achv> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public AchvListAdapter(Context context, List<Achv> dataSource) {

        this.context = context;
        this.dataSource = dataSource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Achv achv = dataSource.get(position);
        if (convertView == null || convertView.getTag() != null) {          //只有数据加载完成后才能重新使用
            convertView = inflater.inflate(R.layout.achv_item, null);
            convertView.setTag(achv);
        }

        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo

        //本地存储
//        String url = achv.getLogo();
//        String fileName = url.substring(url.lastIndexOf("/") + 1);
//        Log.d(Constants.TAG, url + "|" + fileName);
//        BitmapItem bitmapItem = new BitmapItem(context, logo, achv.getLogo(), Constants.achvLogoPath + achv.getId() + "/" + fileName, R.mipmap.achv_default);
//        bitmapItem.show();

        XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.achv_default);
        imageLoader.display(logo,achv.getLogo());

        //title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        if(!StringUtils.isBlank(achv.getName())) {

            title.setText(achv.getName());
        }

        //生产环节
        TextView trade =  (TextView) convertView.findViewById(R.id.trade);
        if(!StringUtils.isBlank(achv.getTradeName())) {

            trade.setText("应用行业:" + achv.getTradeName());
        }else {

            trade.setText("应用行业:暂未填写");
        }

        //comp
        TextView compView = (TextView) convertView.findViewById(R.id.ownername);

        if(StringUtils.isBlank(achv.getOwnerName())) {

            compView.setText("暂未填写");
        }else {

            compView.setText(achv.getOwnerName());
        }

        //price
        TextView price = (TextView)convertView.findViewById(R.id.price);
        if(achv.getPrice() <= 0) {

            price.setText("面议");
        }else {

            price.setText("￥" + achv.getPrice() + "万");
        }
        return convertView;
    }

    public void dataChanged(List<Achv> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
