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
import cn.fundview.app.domain.model.Product;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * 产品适配器
 */
public class ProductListAdapter extends BaseAdapter {

    private List<Product> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, List<Product> dataSource) {

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
        Product product = dataSource.get(position);
        if (convertView == null || convertView.getTag() != null) {          //只有数据加载完成后才能重新使用
            convertView = inflater.inflate(R.layout.product_item, null);
            convertView.setTag(product);
        }

        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
        XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.product_default);
        imageLoader.display(logo, product.getLogo());

        //title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        if(!StringUtils.isBlank(product.getName())) {

            title.setText(product.getName());
        }

        //规格
        TextView trade =  (TextView) convertView.findViewById(R.id.unit);
        if(!StringUtils.isBlank(product.getUnit())) {

            trade.setText("规格:" + product.getUnit());
        }else {

            trade.setText("规格:暂未填写");
        }

        //所属企业
        TextView compView = (TextView) convertView.findViewById(R.id.ownername);

        if(StringUtils.isBlank(product.getCompName())) {

            compView.setText("暂未填写");
        }else {

            compView.setText(product.getCompName());
        }

        //price
        TextView price = (TextView)convertView.findViewById(R.id.price);
        if(product.getPrice() <= 0) {

            price.setText("面议");
        }else {

            price.setText("￥" + product.getPrice() + "元");
        }
        return convertView;
    }

    public void dataChanged(List<Product> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
