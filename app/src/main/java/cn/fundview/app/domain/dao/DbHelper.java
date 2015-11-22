package cn.fundview.app.domain.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import cn.fundview.app.domain.model.UserInfor;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.PreferencesUtils;
import cn.fundview.app.tool.ToastUtils;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.table.ColumnUtils;
import com.lidroid.xutils.db.table.TableUtils;
import com.lidroid.xutils.exception.DbException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具类  使用的是dbUtils,单例
 */
public class DbHelper {

    private static DbHelper instance;

    private DbUtils dbUtils;

    private DbHelper(final Context context) {

        dbUtils = DbUtils.create(context, Constants.DATABASE_NAME, Constants.DATABASE_VERSION,
                new DbUtils.DbUpgradeListener() {

                    @Override
                    public void onUpgrade(DbUtils db, final int oldVersion, final int newVersion) {

                        Log.d(Constants.TAG, "数据库升级...");


                        //个人中心 添加了企业类型字段
                        try {

                            if(oldVersion <= 1) {

                                //第一次更新
                                db.execNonQuery("alter table t_user add  comp_type INTEGER ");
                                db.execNonQuery("alter table t_user add  qr_code_img TEXT ");
                            }

                            if(oldVersion <= 2) {

                                //第二次更新
                                db.execNonQuery("alter table t_user add  area_ids TEXT ");
                            }
                        } catch (DbException e) {
                            ToastUtils.show(context, "数据库更新升级失敗..." + e.getMessage());
                            e.printStackTrace();
                        }
                        ToastUtils.show(context, "数据库更新升级完成...");
                    }
                });
    }

    public static DbHelper getInstance(Context context) {

        if (instance != null) return instance;
        else {
            return new DbHelper(context);
        }
    }

    public DbUtils getDbUtils() {

        return this.dbUtils;
    }

}
