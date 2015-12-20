package cn.fundview.app.action.home;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.CompanyDao;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.ResultListBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 首页 企业风采
 */
public class CompListAction extends AsyncAction<ResultListBean> {

    private static final String TAG = CompListAction.class.getName();
    private AsyncTaskCompleteListener listener;

    public CompListAction(Context context, String url, AsyncTaskCompleteListener listener) {

        super(context, url);

        this.listener = listener;
    }


    @Override
    protected ResultListBean doInBackground(String... params) {

        //需求
        Map<String, String> paramRequ = new HashMap<>();
        paramRequ.put("pageSize", "2");

        if (NetWorkConfig.checkNetwork(context)) {

            try {

                String json = RService.doPostSync(paramRequ, params[0]);
                Log.e("json = " , json);
              return  JSONTools.parseList(json, Company.class);
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.e(TAG, e1.toString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultListBean t) {
        super.onPostExecute(t);

        List<Company> compList = null;
        CompanyDao companyDao = DaoFactory.getInstance(context).getCompDao();
        if (t != null && t.getList() != null && t.getList().size() > 0) {

            compList = t.getList();
            //保存/更新成果
            for (Company item : compList) {

                Company localComp = companyDao.getById(item.getId());
                if (localComp != null) {

                    //更新本地
                    localComp.setUpdateDate(item.getUpdateDate());
                    localComp.setLogo(item.getLogo());

                    localComp.setName(item.getName());
                    localComp.setAreaName(item.getAreaName());
                    localComp.setName(item.getName());
                    localComp.setTradeName(item.getTradeName());
                    localComp.setLogo(item.getLogo());
                    localComp.setUpdateDate(item.getUpdateDate());
                    companyDao.update(localComp);
                } else {

                    item.setRecommendNum(1);//设置为企业推荐值
                    companyDao.save(item);//保存企业信息
                }
            }
        } else {

            compList = companyDao.getRecommendList(2);
        }

        listener.complete(3,Constants.REQUEST_SUCCESS,compList);
    }
}
