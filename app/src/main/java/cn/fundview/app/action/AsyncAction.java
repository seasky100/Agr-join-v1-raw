package cn.fundview.app.action;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2015/11/23 0023.
 * 执行一步任务的actioin
 */
public class AsyncAction<T> extends AsyncTask<String, Integer, T> {


    protected Context context;
    public AsyncAction(Context conext, String url) {

        this.context = conext;
        this.execute(url);
    }

    @Override
    protected T doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
