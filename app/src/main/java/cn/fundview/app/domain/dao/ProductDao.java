package cn.fundview.app.domain.dao;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;
import java.util.Map;

import cn.fundview.app.domain.model.Company;
import cn.fundview.app.domain.model.Product;

/**
 * 企业产品Dao
 */
public class ProductDao extends BaseDao<Product> {


    public ProductDao(Context context) {

        super(context, Product.class);
    }

    public List<Product> getAll() {

        try {
            return dbUtils.findAll(Product.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }


}
