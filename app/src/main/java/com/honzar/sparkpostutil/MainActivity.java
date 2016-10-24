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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SPARKPOST_API_KEY = "insert_your_sparkpost_api_key_here";
    private static final String SENDER_EMAIL = "sender@sparkpost.com";
    private static final String RECIPIENT_EMAIL = "your_email@gmail.com";
    private static final String SUBJECT = "SparkPostUtil - Example";
    private static final String CONTENT = "https://github.com/NoelChew/SparkPostUtil";

    private EditText etSparkPostApiKey, etSenderEmail, etRecipientEmail, etSubject, etContent;
    private Button btnSend;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSparkPostApiKey = (EditText) findViewById(R.id.edit_text_sparkpost_api_key);
        etSenderEmail = (EditText) findViewById(R.id.edit_text_sender_email);
        etRecipientEmail = (EditText) findViewById(R.id.edit_text_recipient_email);
        etSubject = (EditText) findViewById(R.id.edit_text_subject);
        etContent = (EditText) findViewById(R.id.edit_text_content);
        btnSend = (Button) findViewById(R.id.button_send);

        etSparkPostApiKey.setText(SPARKPOST_API_KEY);
        etSenderEmail.setText(SENDER_EMAIL);
        etRecipientEmail.setText(RECIPIENT_EMAIL);
        etSubject.setText(SUBJECT);
        etContent.setText(CONTENT);

        btnSend.setOnClickListener(btnSendOnClickListener);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
    }

    private View.OnClickListener btnSendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }

            File[] front = getAllFilesFromDirectory(new File(getExternalFilesDir(null) + "/" + 20173852 + "/front/"));

            ArrayList<SparkPostFile> files = new ArrayList<>();
            files.add(new SparkPostFile("image/jpeg", "front", getBase64FromFile(front[0])));

            String html = "<html><body>Here is your inline image!<br> <img src=\\\"cid:front\\\">dgthjnfghnfgt</body></html>";

            SparkPostEmailUtil.sendEmail(MainActivity.this,
                    etSparkPostApiKey.getText().toString(),
                    etSubject.getText().toString(),
                    etContent.getText().toString(),
                    new SparkPostRecipient(etRecipientEmail.getText().toString()),
                    new SparkPostSender(etSenderEmail.getText().toString(), getString(R.string.app_name)),
                    html,
                    files,
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Error Sending Email")
                                    .setMessage(errorMessage)
                                    .show();
                            Log.e(TAG, "Error sending SparkPost email: " + errorMessage);
                        }
                    });
        }
    };


    public static File[] getAllFilesFromDirectory(File dir)
    {
        File[] files = null;
        if (dir.exists()) {
            files = dir.listFiles();
        }
        return files;
    }

    public static String getBase64FromFile(File file)
    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
