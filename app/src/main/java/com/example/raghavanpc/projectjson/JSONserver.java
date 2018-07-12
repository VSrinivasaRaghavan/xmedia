package com.example.raghavanpc.projectjson;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JSONserver extends AppCompatActivity {

    ListView lv;
    ProgressDialog dialog;
    ArrayAdapter<String> ad;
//    JSONArray js1=new JSONArray();
    public static JSONArray str=new JSONArray();
    DownloadTask Task;
    static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonserver);
        lv = (ListView) findViewById(R.id.lv);
        Toolbar mtool=(Toolbar)findViewById(R.id.tool);
        setSupportActionBar(mtool);
//        lv.setCacheColorHint();
        Toast.makeText(this,String.valueOf(flag), Toast.LENGTH_SHORT).show();
        if(flag==0)
        download(JSONserver.this);
        else
        loadJSON(str);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        final SearchView search=(SearchView)item.getActionView();
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setBackgroundColor(Color.WHITE);
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class DownloadTask extends AsyncTask<String,String,String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection connection = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                while ((result = reader.readLine()) != null)
                    builder.append(result + "\n");
                Thread.sleep(5000);
                return builder.toString().trim();
            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(JSONserver.this,"ERROR", Toast.LENGTH_SHORT).show();
            }
            return "888888";
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(JSONserver.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setIndeterminate(true);
            dialog.setTitle("IP Configurattion");
            dialog.setMessage("IP is Configuring..Please Wait....");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(JSONserver.this,s, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            try {
                JSONArray jsa = new JSONArray(s);
//                str=s;
                loadJSON(jsa);
//                Toast.makeText(JSONserver.this,String.valueOf(jsa.length()), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void download(Context view){
        Task=new DownloadTask();
        flag = 1;
//        Task.execute("http://thiruninravur.in/qaonemark/test.php");
        Task.execute("http://xmedia.in/crm/follow_up.php");
    }

    public void loadJSON(final JSONArray jsa)
    {
//        js1=jsa;
        JSONserver.str=jsa;
        final ArrayList<String> arl=new ArrayList<>();
        for(int i=0;i<jsa.length();++i)
        {
            JSONObject jso= null;
            try {
                jso = jsa.getJSONObject(i);
                arl.add(jso.getString("project_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ad=new ArrayAdapter<String>(JSONserver.this,android.R.layout.simple_list_item_1,arl);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jn=jsa.getJSONObject(i);
                    Intent in1=new Intent(JSONserver.this,JSONServerpage2.class);
//                            Bundle b1=new Bundle();
//                            b1.putString("key1",jn.toString());
                    in1.putExtra("key2",jn.toString());
                    startActivityForResult(in1,0);
                    overridePendingTransition(R.anim.right_in,R.anim.left_right);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}