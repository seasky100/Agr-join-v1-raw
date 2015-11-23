package cn.fundview.app.action.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.AchvDao;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.dao.RequDao;
import cn.fundview.app.domain.model.Achv;
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
public class AchvListAction extends AsyncAction<ResultBean> {

    private AsyncTaskCompleteListener listener;

    public AchvListAction(Context context, String url, AsyncTaskCompleteListener listener) {

        super(context, url);

        this.listener = listener;
    }


    @Override
    protected ResultBean doInBackground(String... params) {

        //需求
        Map<String, String> param = new HashMap<>();
        param.put("pageSize", "4");

        if (NetWorkConfig.checkNetwork(context)) {

            try {
               return  JSONTools.parseResult(RService.doPostSync(param, params[0]));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultBean t) {
        super.onPostExecute(t);
        List<Achv> achvList = null;

        if (t != null && t.getStatus() == cn.fundview.app.domain.webservice.util.Constants.REQUEST_SUCCESS) {

            JSONObject jsonObject = JSON.parseObject(t.getResult());
            if (jsonObject != null) {

                String jsonResult = jsonObject.getString("resultList");
                if (!StringUtils.isBlank(jsonResult)) {

                    achvList = JSON.parseArray(jsonResult, Achv.class);
                }
            }
        }
        AchvDao achvDao = DaoFactory.getInstance(context).getAchvDao();
        if (achvList != null && achvList.size() > 0) {

            //保存/更新成果
            for (Achv item : achvList) {

                Achv localAchv = achvDao.getById(item.getId());
                if (localAchv != null) {

                    //更新本地
                    localAchv.setUpdataDate(item.getUpdataDate());
                    if (localAchv.getLogo() != item.getLogo()) {

                        //本地的logo 和服务端的logo 不一致的时候,更新logo
                        item.setOldLocalPath(localAchv.getLogo());
                        localAchv.setOldLocalPath(localAchv.getLogo());
                        localAchv.setLogo(item.getLogo());
                    }
                    localAchv.setName(item.getName());
                    localAchv.setPrice(item.getPrice());
                    localAchv.setOwnerName(item.getOwnerName());
                    localAchv.setTradeName(item.getTradeName());
                    localAchv.setRecommend(1);//设置为推荐成果
                    achvDao.update(localAchv);//更新本地
                } else {

                    item.setRecommend(1);//设置为推荐成果
                    achvDao.save(item);//保存成果
                }
            }
        } else {

            achvList = achvDao.getRecommendList(4);
        }


        listener.complete(2,Constants.REQUEST_SUCCESS,achvList);
    }
}
