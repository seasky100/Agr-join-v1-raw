package cn.fundview.app.action.home;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.dao.ProductDao;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.FundProject;
import cn.fundview.app.model.ResultListBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 融资项目
 */
public class FundProjListAction extends AsyncAction<ResultListBean> {

    private AsyncTaskCompleteListener listener;

    public FundProjListAction(Context context, String url, AsyncTaskCompleteListener listener) {

        super(context, url);

        this.listener = listener;
    }


    @Override
    protected ResultListBean doInBackground(String... params) {

        //需求
        Map<String, String> param = new HashMap<>();
        param.put("pageSize", "2");

        if (NetWorkConfig.checkNetwork(context)) {

            try {
                return JSONTools.parseList(RService.doPostSync(param, params[0]), FundProject.class);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultListBean t) {
        super.onPostExecute(t);

        ProductDao productDao = DaoFactory.getInstance(context).getProductDao();
        List<FundProject> fundProjects = null;
        if (t != null && t.getList() != null && t.getList().size() > 0) {

            fundProjects = t.getList();

        }
        listener.complete(6, Constants.REQUEST_SUCCESS, fundProjects);
    }
}
