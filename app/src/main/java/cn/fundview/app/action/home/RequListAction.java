package cn.fundview.app.action.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.dao.RequDao;
import cn.fundview.app.domain.model.Requ;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.ResultBean;
import cn.fundview.app.model.ResultListBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 */
public class RequListAction extends AsyncAction<ResultBean> {

    private AsyncTaskCompleteListener listener;

    public RequListAction(Context context, String url, AsyncTaskCompleteListener listener) {

        super(context, url);

        this.listener = listener;
    }


    @Override
    protected ResultBean doInBackground(String... params) {

        //需求
        Map<String, String> paramRequ = new HashMap<>();
        paramRequ.put("pageSize", "4");

        if (NetWorkConfig.checkNetwork(context)) {

            try {
               return  JSONTools.parseResult(RService.doPostSync(paramRequ, params[0]));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultBean t) {
        super.onPostExecute(t);

        List<Requ> requList = null;
        if (t != null && t.getStatus() == cn.fundview.app.domain.webservice.util.Constants.REQUEST_SUCCESS) {

            JSONObject jsonObject = JSON.parseObject(t.getResult());
            if (jsonObject != null) {

                String jsonResult = jsonObject.getString("resultList");
                if (!StringUtils.isBlank(jsonResult)) {

                    requList = JSON.parseArray(jsonResult, Requ.class);
                }
            }
        }

        RequDao requDao = DaoFactory.getInstance(context).getRequDao();

        if (requList != null && requList.size() > 0) {

            //保存/更新成果
            for (Requ item : requList) {

                Requ localRequ = requDao.getById(item.getId());
                if (localRequ != null) {

                    //更新本地
                    localRequ.setUpdateTime(item.getUpdateTime());
                    if (localRequ.getLogo() != item.getLogo()) {

                        //本地的logo 和服务端的logo 不一致的时候,更新logo
                        item.setLogoLocalPath(localRequ.getLogo());
                        localRequ.setLogoLocalPath(localRequ.getLogo());
                        localRequ.setLogo(item.getLogo());
                    }
                    localRequ.setName(item.getName());
                    localRequ.setFinPlan(item.getFinPlan());
                    localRequ.setOwnerName(item.getOwnerName());
                    localRequ.setTradeName(item.getTradeName());
                    localRequ.setRecommend(1);//设置为推荐成果
                    requDao.update(localRequ);//更新本地
                } else {

                    item.setRecommend(1);//设置为推荐成果
                    requDao.save(item);//保存成果
                }
            }
        } else {

            requList = requDao.getRecommendList(4);
        }

        listener.complete(1,Constants.REQUEST_SUCCESS,requList);
    }
}
