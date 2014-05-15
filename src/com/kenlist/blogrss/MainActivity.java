
package com.kenlist.blogrss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.bool;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

import org.blogrsssdk.*;

public class MainActivity extends Activity {

    private static ListView rssListView;

    private static String[] from = {
            "title", "date"
    };

    private static int[] to = {
            R.id.title, R.id.date
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ProgressDialog m_progressDialog;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            initView(getView());
        }

        private void initView(View view) {
            rssListView = (ListView)view.findViewById(R.id.rssListView);

            BlogRSSSDK.getInstance().setDelegate(new BlogRSSSDKDelegate() {

                @Override
                public void onRSSFetchedWithRetCode(int retCode, RSSItem[] rssItems) {

                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map = null;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    for (int i = 0; i < rssItems.length; i++) {
                        map = new HashMap<String, Object>();
                        map.put("title", rssItems[i].title);
                        map.put("date", dateFormat.format(rssItems[i].pubDate));
                        map.put("url", rssItems[i].link);
                        list.add(map);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.item,
                            from, to);
                    rssListView.setAdapter(adapter);
                    m_progressDialog.dismiss();
                }

            });
            BlogRSSSDK.getInstance().fetchRSS();
            m_progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);

            rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    HashMap<String, Object> map = (HashMap<String, Object>)parent.getItemAtPosition(position);
                    String url = (String) map.get("url");
                    
                    
                    Intent intent=new Intent();
                    intent.putExtra("url", url);
                    intent.setClass(getActivity(), WebActivity.class);
                    startActivity(intent); 
                }
                
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
