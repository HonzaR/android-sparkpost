package com.honzar.sparkpostutil;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.honzar.sparkpostutil.library.EmailListener;
import com.honzar.sparkpostutil.library.SparkPostEmailUtil;
import com.honzar.sparkpostutil.library.SparkPostFile;
import com.honzar.sparkpostutil.library.SparkPostRecipient;
import com.honzar.sparkpostutil.library.SparkPostSender;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SPARKPOST_API_KEY = "insert_your_sparkpost_api_key_here"; //"6028f49448e67b282606a0fbee086f36df40ef82"
    private static final String SENDER_EMAIL = "sender@sparkpost.com";
    private static final String RECIPIENT_EMAIL = "your_email@gmail.com";
    private static final String SUBJECT = "SparkPostUtil - Example";
    private static final String CONTENT = "https://github.com/HonzaR/SparkPostUtil";

    private EditText etSparkPostApiKey, etSenderEmail, etRecipientEmail, etSubject, etContent;
    private Button btnSend;
    private Button btnCancel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSparkPostApiKey = findViewById(R.id.edit_text_sparkpost_api_key);
        etSenderEmail = findViewById(R.id.edit_text_sender_email);
        etRecipientEmail = findViewById(R.id.edit_text_recipient_email);
        etSubject = findViewById(R.id.edit_text_subject);
        etContent = findViewById(R.id.edit_text_content);
        btnSend = findViewById(R.id.button_send);
        btnCancel = findViewById(R.id.button_cancel);

        etSparkPostApiKey.setText(SPARKPOST_API_KEY);
        etSenderEmail.setText(SENDER_EMAIL);
        etRecipientEmail.setText(RECIPIENT_EMAIL);
        etSubject.setText(SUBJECT);
        etContent.setText(CONTENT);

        btnSend.setOnClickListener(btnSendOnClickListener);
        btnCancel.setOnClickListener(btnCancelOnClickListener);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");

        Bitmap bm = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.mipmap.ic_launcher);
        saveBitmapToFile(getExternalFilesDir(null), "launcherIcon.png", bm, Bitmap.CompressFormat.PNG, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        deleteFileOrDirectoryIfCan(new File(MainActivity.this.getExternalFilesDir(null) + "/"));
    }

    private View.OnClickListener btnCancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            SparkPostEmailUtil.cancelSending(MainActivity.this);
        }
    };

    private View.OnClickListener btnSendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            File[] fs = getAllFilesFromDirectory(new File(getExternalFilesDir(null) + "/"));
            ArrayList<SparkPostFile> files = new ArrayList<>();

            for (int i = 0; i < fs.length; i++) {
                files.add(new SparkPostFile("image/png", "launcherIcon" + i, getBase64FromFile(fs[i])));
            }

            String html = "<html><body>Here is your inline image!<br> <img src=\\\"cid:launcherIcon\\\"></body></html>";
            String replyTo = "ddd@gmail.com";

            SparkPostEmailUtil.sendEmail(MainActivity.this,
                    etSparkPostApiKey.getText().toString(),
                    etSubject.getText().toString(),
                    etContent.getText().toString(),
                    new SparkPostRecipient(etRecipientEmail.getText().toString()),
                    new SparkPostSender(etSenderEmail.getText().toString(), getString(R.string.app_name)),
                    html,
                    files,
                    replyTo,
                    new EmailListener() {
                        @Override
                        public void onSuccess() {

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Success")
                                    .setMessage("Email has been sent successfully.")
                                    .show();
                        }

                        @Override
                        public void onError(String errorMessage) {

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (!errorMessage.equals("Canceled")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Error Sending Email")
                                        .setMessage(errorMessage)
                                        .show();
                            }

                            Log.e(TAG, "Error sending SparkPost email: " + errorMessage);
                        }
                    });
        }
    };

    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality)
    {

        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

            fos.close();

            return true;
        }
        catch (IOException e) {
            Log.e("app",e.getMessage());

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    public File[] getAllFilesFromDirectory(File dir)
    {
        File[] files = null;
        if (dir.exists()) {
            files = dir.listFiles();
        }
        return files;
    }

    public String getBase64FromFile(File file)
    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public boolean deleteFileOrDirectoryIfCan(File fileOrDirectory)
    {
        try {
            if (fileOrDirectory.isDirectory()) {
                for (File child : fileOrDirectory.listFiles()) {
                    deleteFileOrDirectoryIfCan(child);
                }
            }
            fileOrDirectory.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
