package com.eke.cust.tabmore.about_activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eke.cust.Constants;
import com.eke.cust.R;
import com.eke.cust.base.BaseActivity;
import com.eke.cust.model.AppInfo;
import com.eke.cust.net.ClientHelper;
import com.eke.cust.net.ServerUrl;
import com.eke.cust.tabmore.barcode_activity.QRCodeUtil;
import com.eke.cust.utils.AppUtils;
import com.eke.cust.utils.JSONUtils;
import com.hyphenate.util.DensityUtil;

import org.droidparts.annotation.inject.InjectView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutActivity extends BaseActivity {
    @InjectView(id = R.id.tv_current_version)
    private TextView mTextView_tv_current_version;
    @InjectView(id = R.id.tv_kefu)
    private TextView mTextView_tv_kefu;
    @InjectView(id = R.id.iv_barcode)
    //二维码
    private ImageView mIv_barcode;

    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            super.dispatchMessage(msg);

            if (msg != null) {
                switch (msg.what) {
                    case Constants.NO_NETWORK:
                        break;

                    case Constants.TAG_SUCCESS:
                        Bundle bundle = msg.getData();
                        String request_url = bundle.getString("request_url");
                        String resp = bundle.getString("resp");
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            String result = jsonObject.optString("result", "");
                            if (result.equals(Constants.RESULT_SUCCESS)) {
                                if (request_url.equals(ServerUrl.METHOD_getAboutUs)) {
                                    JSONObject data = JSONUtils.getJSONObject(jsonObject, "data", null);
                                    JSONArray jsonArray = JSONUtils.getJSONArray(data, "list", null);
                                    boolean isupdate = JSONUtils.getBoolean(data, "isupdate", false);
                                    ArrayList<AppInfo> appInfos = JSONUtils.getObjectList(jsonArray, AppInfo.class);
                                    mTextView_tv_kefu.setText("客服电话：" + appInfos.get(1).paramdata);
                                    if (isupdate) {
                                        showUpdateDlg(appInfos.get(0).paramdata, appInfos.get(1).paramdata);
                                    }

                                }

                            } else if (result.equals(Constants.RESULT_ERROR)) {
                                String errorMsg = jsonObject.optString("errorMsg", "出错!");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case Constants.TAG_FAIL:
                        Toast.makeText(getApplicationContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.TAG_EXCEPTION:
                        Toast.makeText(getApplicationContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        }
    };

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_tab_more_about);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于");
        registerLeftImageView(R.drawable.arrow_back);

        mTextView_tv_current_version.setText("v" + AppUtils.getVersionName(mContext));
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = new JSONObject();
                try {
                    obj.put("paramdata", AppUtils.getVersionName(mContext));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ClientHelper clientHelper = new ClientHelper(AboutActivity.this,
                        ServerUrl.METHOD_getAboutUs, obj.toString(), mHandler);
                clientHelper.setShowProgressMessage("正在获取数据...");
                clientHelper.isShowProgress(true);
                clientHelper.sendPost(true);
            }
        }, 200);

        Bitmap qrcodeBitmap = QRCodeUtil.createQRImage("www.ekeae.com", DensityUtil.dip2px(mContext, 80),
                DensityUtil.dip2px(mContext, 80), BitmapFactory.decodeResource(getResources(), R.drawable.icon_wechat_code), null);
        if (qrcodeBitmap != null) {
            mIv_barcode.setImageBitmap(qrcodeBitmap);
        }
    }


    private boolean isNewVersion(String version) {
        String[] current = AppUtils.getVersionName(mContext).split("\\.");
        if (current == null || current.length < 3) {
            return false;
        }
        if (version == null) {
            return false;
        }
        String[] getversion = version.split("\\.");
        if (getversion == null || getversion.length < 3) {
            return false;
        }
        if (Integer.valueOf(getversion[0].trim()) > Integer.valueOf(current[0].trim())) {
            return true;
        } else if (Integer.valueOf(getversion[1].trim()) > Integer.valueOf(current[1].trim())) {
            return true;
        } else if (Integer.valueOf(getversion[2].trim()) > Integer.valueOf(current[2].trim())) {
            return true;
        }

        return false;
    }

    public void checkUpdate(View view) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("paramdata", AppUtils.getVersionName(mContext));
            ClientHelper clientHelper = new ClientHelper(AboutActivity.this,
                    ServerUrl.METHOD_checkNewVersion, obj.toString(), mHandler);
            clientHelper.setShowProgressMessage("正在检测新版本...");
            clientHelper.isShowProgress(true);
            clientHelper.sendPost(true);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void showUpdateDlg(final String version, final String url) {
        final Dialog dlg = new Dialog(this, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewContent = inflater.inflate(R.layout.dlg_update, null);

        TextView tv_tip = (TextView) viewContent.findViewById(R.id.tv_tip);
        tv_tip.setText("发现新版本v" + version);
        Button btn_update = (Button) viewContent.findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (url != null && !url.equals("")) {
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        });

        dlg.setContentView(viewContent);
        dlg.setCanceledOnTouchOutside(true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Window window = dlg.getWindow();
        window.setGravity(Gravity.CENTER);
        dlg.show();
    }

}
