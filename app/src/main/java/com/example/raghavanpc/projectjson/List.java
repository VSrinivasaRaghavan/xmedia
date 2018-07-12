package com.example.raghavanpc.projectjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String det="{'datas':[{'project_id':1047,'project_name':'Ananda Book Depot CMS','company_name':'Ananda Book Depot','Contact_Number-1':'0','customer_name':'Vivek'},{ 'project_id':'167','project_name':'C D Presentation',company_name:'Airbreeze','Contact_Number-1':'920000000000','customer_name':'Jaishankar'},{'project_id':'193','project_name':'calief  Sweevia - Logo design','company_name':'ASR Consumer Products','Contact_Number-1':'9500077098','customer_name':'Sridhar'},{'project_id':'890','project_name':'Dhinamalar ellai Social media optimaisation.','company_name':'Dhinamalar','Contact_Number-1':'0','customer_name':'Nellai'}]}";
        ListView list=(ListView)findViewById(R.id.list);
        try {
            JSONObject jso = new JSONObject(det);
            final JSONArray jsa=jso.getJSONArray("datas");
            ArrayList<String> al=new ArrayList<String>();
            //al.add(  test  );
            for(int i=0;i<jsa.length();++i)
            {
                JSONObject jsn=jsa.getJSONObject(i);
                al.add(jsn.getString(  "project_id"  ));
                //Toast.makeText(this, jsn.getString(  project_id  ), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this,String.valueOf(al.size()), Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
            list.setAdapter(ad);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        JSONObject jsn = jsa.getJSONObject(i);
                        Toast.makeText(List.this, jsn.getString("project_name"), Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Toast.makeText(List.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
