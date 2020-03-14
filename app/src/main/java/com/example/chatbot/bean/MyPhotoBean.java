package com.example.chatbot.bean;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * 创建日期：
 * <p>
 * 描述：照片处理类
 **/
public class MyPhotoBean {
    Context context;
    private File file;
    private Uri outUri=null;

    public Uri getOutUri() {
        return outUri;
    }



    public MyPhotoBean(Context context){
        this.context=context;
        file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/cut.jpg");
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outUri = Uri.fromFile(file);
    }
    public   Intent CutForPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");  // 要裁剪的图片URI
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);  // aspectX：aspectY 裁剪比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 1024);  // 输出图片大小
        intent.putExtra("outputY", 1024);
        intent.putExtra("return-data", false);  // 是否以bitmap方式返回,不返回缩略图，缩略图可设为true，大图一定要设为false，返回URI
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);  // 输出的图片的URI
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 返回格式
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        return intent;

        /*//设置裁剪之后的图片路径文件
        Intent intent1 = new Intent("com.android.camera.action.CROP");
        intent1.setDataAndType(uri, "image/*");
        intent1.putExtra("crop", "true");// crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent1.putExtra("aspectX", 1); // aspectX,aspectY 是宽高的比例
        intent1.putExtra("aspectY", 1);
        intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);//压缩图片
        intent1.putExtra("outputX", 360);//设置要裁剪的宽高
        intent1.putExtra("outputY", 360);
        intent1.putExtra("scale", true);
        intent1.putExtra("scaleUpIfNeeded", true);//图片裁剪做填充拉伸
        intent1.putExtra("return-data", false);//如果图片过大，会导致oom，这里设置为false
        return intent1;*/
    }
}
