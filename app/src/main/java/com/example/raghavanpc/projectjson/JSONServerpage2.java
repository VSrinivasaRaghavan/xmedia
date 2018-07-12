package com.example.raghavanpc.projectjson;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class JSONServerpage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonserverpage2);
        TextView pn = (TextView) findViewById(R.id.pname);
        TextView name = (TextView) findViewById(R.id.name);
        TextView desc = (TextView) findViewById(R.id.desc);
        TextView desc1 = (TextView) findViewById(R.id.desc1);
        int width = 0;
        int height = 0;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = (int) (dm.widthPixels * 0.25);
        ImageView im = (ImageView) findViewById(R.id.im);
        Bitmap bt = BitmapFactory.decodeResource(getResources(), R.drawable.ab1);
        double d = ((double)width)/((double)bt.getWidth());
        height = (int)(d*bt.getHeight());
//        Bitmap b1=Bitmap.createScaledBitmap(bt,width,height,false);
        RoundedBitmapDrawable rb = RoundedBitmapDrawableFactory.create(getResources(), bt);
//        rb.setBounds(0,0,120,120);
        rb.setCircular(true);
        rb.setAntiAlias(true);
        im.setImageDrawable(rb);
        String phn="";
        ImageButton imb = (ImageButton) findViewById(R.id.call);
        ImageButton imb1=(ImageButton)findViewById(R.id.wapp);
        ImageButton im2=(ImageButton)findViewById(R.id.sms);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                JSONObject jo = new JSONObject(extras.getString("key2"));
                pn.setText(jo.getString("project_name"));
                name.setText(jo.getString("company_name"));
                desc1.setText(jo.getString("project_description"));
                phn=jo.getString("Contact_Number-1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final String finalPhn = phn;
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JSONServerpage2.this,finalPhn,Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+finalPhn));
                startActivity(callIntent);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("sms:"+finalPhn));
                startActivity(i);
            }
        });
        imb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager=JSONServerpage2.this.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    String url="https://api.whatsapp.com/send?phone="+"+91"+finalPhn+"&text=" + URLEncoder.encode("abcd", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager)!= null) {
                        JSONServerpage2.this.startActivity(i);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}