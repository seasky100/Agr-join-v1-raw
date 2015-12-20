package cn.fundview.app.action.home;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.app.action.AsyncAction;
import cn.fundview.app.domain.dao.DaoFactory;
import cn.fundview.app.domain.dao.ProductDao;
import cn.fundview.app.domain.model.Product;
import cn.fundview.app.domain.webservice.RService;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.ResultListBean;
import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.tool.json.JSONTools;
import cn.fundview.app.view.AsyncTaskCompleteListener;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 推荐产品
 */
public class ProductListAction extends AsyncAction<ResultListBean> {

    private AsyncTaskCompleteListener listener;

    public ProductListAction(Context context, String url, AsyncTaskCompleteListener listener) {

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
                return JSONTools.parseList(RService.doPostSync(param, params[0]), Product.class);
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
        List<Product> productList = null;
        if (t != null && t.getList() != null && t.getList().size() > 0) {

            productList = t.getList();
            for (Product item : productList) {

                if (item != null) {

                    Product localItem = productDao.getById(item.getId());
                    if (localItem == null) {

                        //添加新产品
                        item.setRecommend(1);
                        productDao.save(item);
                    } else if (localItem.getUpdateDate() != item.getUpdateDate()) {

                        //更新产品信息
                        localItem.setName(item.getName());//需求名
                        localItem.setCompName(item.getCompName());//企业名称
                        localItem.setPrice(item.getPrice());//产品价格
                        localItem.setUnit(item.getUnit());//产品规格
                        localItem.setRecommend(1);
                        localItem.setLogo(item.getLogo());
                        localItem.setUpdateDate(item.getUpdateDate());
                        productDao.update(localItem);
                    }
                }
            }
        } else {

            productList = productDao.getRecommendList(2);
        }

        listener.complete(5, Constants.REQUEST_SUCCESS, productList);
    }
}
