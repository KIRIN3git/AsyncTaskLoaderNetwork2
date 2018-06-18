package com.example.shinji_win.asynctaskloadernetwork2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shinji_win.asynctaskloadernetwork2.util.AsyncLoadingTask;
import com.example.shinji_win.asynctaskloadernetwork2.util.IOUtils;

import org.json.JSONObject;

// 引数は必要に応じて変更
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private AsyncLoadingTask mLoader;
    private ProgressDialog mDialog;

    private static final String QUERY_ARG_URL = "url";
    private static final int LOADER_TAG_METADATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataBootstrapIfNecessary();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (mLoader != null) {
            mLoader.stopLoading();
            mLoader = null;
        }
    }

    private void fetchDataBootstrapIfNecessary() {
        Bundle args = new Bundle();
        args.putString(QUERY_ARG_URL, "http://zipcloud.ibsnet.co.jp/api/search?zipcode=1640003");
        getSupportLoaderManager().initLoader(LOADER_TAG_METADATA, args, this).forceLoad();
        mDialog.show();
        return;
    }

    private void initViews() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(R.string.app_name);
        mDialog.setMessage("データを更新しています...");
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mLoader != null) {
                    mLoader.stopLoading();
                    mLoader = null;
                }
            }
        });
    }

    private void onDataLoaded(JSONObject data) {
        if (data != null) {
            Log.w( "DEBUG_DATA", "data = " + data );

            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(data.toString());
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }


    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        String url = args.getString(QUERY_ARG_URL);
        if (!TextUtils.isEmpty(url)) {
            mLoader = new AsyncLoadingTask(getApplicationContext(), url);
            return mLoader;
        }
        return null;
    }

    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        getLoaderManager().destroyLoader(loader.getId());
        onDataLoaded(data);
    }

    public void onLoaderReset(Loader<JSONObject> loader) {}
}
