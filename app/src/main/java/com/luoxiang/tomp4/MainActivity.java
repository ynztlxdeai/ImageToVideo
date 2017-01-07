package com.luoxiang.tomp4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity
        extends AppCompatActivity
{

    @Bind(R.id.main_btn_start)
    Button mMainBtnStart;
    @Bind(R.id.main_btn_upload)
    Button mMainBtnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_btn_start,
              R.id.main_btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_start:
                Log.e("performJcodec: ", "执行点击事件");
                performJcodec();
                break;
            case R.id.main_btn_upload:
                break;
        }
    }

    private void performJcodec() {
        try {
            Log.e("performJcodec: ", "执行开始");
            SequenceEncoderMp4 se   = null;
            File               file = new File(Constants.FILE_SCREEN_FLODER);


            File out = new File(Constants.FILE_FOLDER, "jcodec_enc.mp4");
            se = new SequenceEncoderMp4(out);

            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].exists()) { break; }
                Bitmap frame = BitmapUtil.decodeSampledBitmapFromFile(files[i].getAbsolutePath() , 480 , 320);

                se.encodeImage(frame);
                Log.e("performJcodec: ", "执行到的图片是 " + i);
            }
            se.finish();
            Log.e("performJcodec: ", "执行完成");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(out)));
        } catch (IOException e) {
            Log.e("performJcodec: ", "执行异常 " + e.toString());
        }
    }
}
