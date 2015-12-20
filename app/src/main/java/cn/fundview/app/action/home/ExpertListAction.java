package cn.fundview.app.action.home;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.ExpertDao;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.model.Expert;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.ResultListBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 首页 专家资源
 */
public class ExpertListAction extends AsyncAction<ResultListBean> {

    private static final String TAG = ExpertListAction.class.getName();
    private AsyncTaskCompleteListener listener;

    public ExpertListAction(Context context, String url, AsyncTaskCompleteListener listener) {

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
              return  JSONTools.parseList(json, Expert.class);
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

        List<Expert> expertList = null;
        ExpertDao expertDao = DaoFactory.getInstance(context).getExpertDao();
        if (t != null && t.getList() != null && t.getList().size() > 0) {

            expertList = t.getList();
            for (Expert item : expertList) {

                if (item != null) {

                    Expert localItem = expertDao.getById(item.getId());
                    if (localItem == null) {

                        //添加新专家
                        item.setRecommendNum(1);
                        expertDao.save(item);
                    } else if (localItem.getUpdateDate() != item.getUpdateDate()) {

                        //更新企业信息
                        localItem.setAreaName(item.getAreaName());
                        localItem.setName(item.getName());
                        localItem.setTradeName(item.getTradeName());
                        localItem.setRecommendNum(1);
                        localItem.setLogo(item.getLogo());
                        localItem.setProfessionalTitle(item.getProfessionalTitle());
                        localItem.setUpdateDate(item.getUpdateDate());
                        expertDao.update(localItem);
                    }
                }
            }
        } else {

            expertList = expertDao.getRecommendList(2);
        }

        listener.complete(4,Constants.REQUEST_SUCCESS,expertList);
    }
}
