package co.huiqu.webapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import co.huiqu.webapp.download.DownLoadUtils;
import co.huiqu.webapp.download.DownloadApk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(this);
        //2.删除已存在的Apk
        DownloadApk.removeFile(this);


        String localPackageName = getApplicationContext().getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = getApplicationContext().getPackageManager().getPackageInfo(localPackageName, 0);
            //Toast.makeText(getApplicationContext(),"当前版本号"+packageInfo.versionCode,Toast.LENGTH_LONG).show();

            Toast.makeText(getApplicationContext(),getApplicationContext().getFilesDir().getAbsolutePath(),Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                    DownloadApk.downloadApk(getApplicationContext(), "http://211.149.234.199:23335/AndroidVersionManager/update_pakage/baidu.apk", "Hobbees更新", "Hobbees");
                } else {
                    DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        //4.反注册广播接收器
        DownloadApk.unregisterBroadcast(this);
        super.onDestroy();
    }
}
