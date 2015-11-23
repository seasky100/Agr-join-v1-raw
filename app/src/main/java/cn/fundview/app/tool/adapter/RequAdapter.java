package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;

import java.net.ContentHandler;
import java.util.List;
import java.util.Map;

import cn.fundview.R;
import cn.fundview.app.domain.model.Requ;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.BitmapItem;

/**
 * 技术需求适配器
 */
public class RequAdapter extends BaseAdapter {

    private List<Requ> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public RequAdapter(Context context, List<Requ> dataSource) {

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
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.requ_item, null);
        }
        Requ requ = dataSource.get(position);

        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
        String url = requ.getLogo();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        BitmapItem bitmapItem = new BitmapItem(context, logo, requ.getLogo(), Constants.requLogoPath + requ.getId() + "/" + fileName, R.mipmap.company_logo);
        bitmapItem.show();

        //title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        if(!StringUtils.isBlank(requ.getName())) {

            title.setText(requ.getName());
        }

        //生产环节
        TextView hj =  (TextView) convertView.findViewById(R.id.hj);
        if(!StringUtils.isBlank(requ.getHj())) {

            hj.setText("生产环节:" + requ.getHj());
        }else {

            hj.setText("生产环节:暂未填写");
        }

        //comp
        TextView compView = (TextView) convertView.findViewById(R.id.compname);

        if(StringUtils.isBlank(requ.getOwnerName())) {

            compView.setText("暂未填写");
        }else {

            compView.setText(requ.getOwnerName());
        }

        //price
        TextView price = (TextView)convertView.findViewById(R.id.price);
        if(requ.getFinPlan() <= 0) {

            price.setText("面议");
        }else {

            price.setText("￥" + requ.getFinPlan() + "万");
        }



        return convertView;
    }

    public void dataChanged(List<Requ> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
