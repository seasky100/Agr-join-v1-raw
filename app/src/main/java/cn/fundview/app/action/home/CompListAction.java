package cn.fundview.app.action.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.CompanyDao;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.ResultBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 首页 企业风采
 */
public class CompListAction extends AsyncAction<ResultBean> {

    private AsyncTaskCompleteListener listener;

    public CompListAction(Context context, String url, AsyncTaskCompleteListener listener) {

        super(context, url);

        this.listener = listener;
    }


    @Override
    protected ResultBean doInBackground(String... params) {

        //需求
        Map<String, String> paramRequ = new HashMap<>();
        paramRequ.put("pageSize", "2");

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

        List<Company> compList = null;
        if (t != null && t.getStatus() == Constants.REQUEST_SUCCESS) {

            JSONObject jsonObject = JSON.parseObject(t.getResult());
            if (jsonObject != null) {

                String jsonResult = jsonObject.getString("resultList");
                if (!StringUtils.isBlank(jsonResult)) {

                    compList = JSON.parseArray(jsonResult, Company.class);
                }
            }
        }

        CompanyDao companyDao = DaoFactory.getInstance(context).getCompDao();

        if (compList != null && compList.size() > 0) {

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
//                                    localItem.setLocalLogo(localItem.getLogo());//删除老图片的时候用
                    localComp.setLogo(item.getLogo());
                    localComp.setUpdateDate(item.getUpdateDate());
                    companyDao.update(localComp);
                } else {

                    localComp.setRecommendNum(1);//设置为企业推荐值
                    companyDao.save(item);//保存企业信息
                }
            }
        } else {

            compList = companyDao.getRecommendList(2);
        }

        listener.complete(1,Constants.REQUEST_SUCCESS,compList);
    }
}
