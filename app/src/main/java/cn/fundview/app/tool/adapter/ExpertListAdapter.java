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
import cn.fundview.app.domain.model.Expert;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 *专家适配器
 */
public class ExpertListAdapter extends BaseAdapter {

    private List<Expert> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public ExpertListAdapter(Context context, List<Expert> dataSource) {

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
        Expert expert = dataSource.get(position);
        if (convertView == null || convertView.getTag() != null) {          //只有数据加载完成后才能重新使用
            convertView = inflater.inflate(R.layout.expert_item, null);
            convertView.setTag(expert);
        }

        //logo
        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
        XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.company_logo);
        imageLoader.display(logo, expert.getLogo());

        //title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        if(!StringUtils.isBlank(expert.getName())) {

            title.setText(expert.getName());
        }

        //专业职称
        TextView professionalTitleTextView = (TextView) convertView.findViewById(R.id.professionalTitle);
        if(!StringUtils.isBlank(expert.getProfessionalTitle())) {

            professionalTitleTextView.setText(expert.getProfessionalTitle());
        }

        //生产环节
        TextView trade =  (TextView) convertView.findViewById(R.id.trade);
        if(!StringUtils.isBlank(expert.getTradeName())) {

            trade.setText("行业:" + expert.getTradeName());
        }else {

            trade.setText("应用行业:暂未填写");
        }

        //comp
        TextView compView = (TextView) convertView.findViewById(R.id.area);

        if(StringUtils.isBlank(expert.getAreaName())) {

            compView.setText("暂未填写");
        }else {

            compView.setText(expert.getAreaName());
        }
        return convertView;
    }

    public void dataChanged(List<Expert> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
