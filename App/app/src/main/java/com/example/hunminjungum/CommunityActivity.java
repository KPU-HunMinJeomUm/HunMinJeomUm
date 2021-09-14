package com.example.hunminjungum;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class CommunityActivity extends TabActivity {
    String session_id, id1, id2, id3, id4;
    ListView freelist,infolist,suggestlist,repostlist;
    SingleAdapter adapter1,adapter2,adapter3,adapter4;
    int postnum1, postnum2, postnum3, postnum4;
    int view1, view2, view3, view4, postnum_1, postnum_2, postnum_3, postnum_4;
    ArrayList<Integer> array1 = new ArrayList<>();
    ArrayList<Integer> array2 = new ArrayList<>();
    ArrayList<Integer> array3 = new ArrayList<>();
    ArrayList<Integer> array4 = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        String txtColor = "#000000";

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        session_id = sharedPreferences.getString("id","");

        TabHost tabHost = getTabHost();

        TabHost.TabSpec ts1 = tabHost.newTabSpec("freeboard");
        ts1.setContent(R.id.freeboard);
        ts1.setIndicator("자유게시판");
        tabHost.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("infoboard");
        ts2.setContent(R.id.infoboard);
        ts2.setIndicator("정보게시판");
        tabHost.addTab(ts2);

        TabHost.TabSpec ts3 = tabHost.newTabSpec("suggestboard");
        ts3.setContent(R.id.suggestboard);
        ts3.setIndicator("건의게시판");
        tabHost.addTab(ts3);

        TabHost.TabSpec ts4 = tabHost.newTabSpec("reportboard");
        ts4.setContent(R.id.reportboard);
        ts4.setIndicator("신고게시판");
        tabHost.addTab(ts4);

        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor(txtColor));
        }

        freelist=(ListView)findViewById(R.id.freeboardlist);
        infolist=(ListView)findViewById(R.id.infoboardlist);
        suggestlist=(ListView)findViewById(R.id.suggetboardlist);
        repostlist=(ListView)findViewById(R.id.reportboardlist);

        adapter1 = new SingleAdapter();
        adapter2 = new SingleAdapter();
        adapter3 = new SingleAdapter();
        adapter4 = new SingleAdapter();


        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            id1 = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            view1 = jsonObject.getInt("view");
                            String date = jsonObject.getString("date");
                            postnum1 = jsonObject.getInt("postnum");
                            adapter1.addPost(new SinglePost(title, id1, date, view1, postnum1));
                            freelist.setAdapter(adapter1);
                            array1.add(i, postnum1);
                        }
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        ViewFreeBoardRequest viewFreeBoardRequest = new ViewFreeBoardRequest(responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(CommunityActivity.this);
        queue1.add(viewFreeBoardRequest);

        freelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
                view1++;
                postnum_1 = array1.get(position);
                Response.Listener<String> responseListener_1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), FreeBoardListView.class);
                                intent.putExtra("postnum",postnum_1);
                                startActivity(intent);
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ModifyFreeViewRequest modifyFreeViewRequest = new ModifyFreeViewRequest(postnum_1, view1, responseListener_1);
                RequestQueue queue1 = Volley.newRequestQueue(CommunityActivity.this);
                queue1.add(modifyFreeViewRequest);
            }
        });

        Button write1 = (Button)findViewById(R.id.write1);
        if(!session_id.equals("")){
            write1.setVisibility(View.VISIBLE);
            write1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CommunityActivity.this,WriteFreeActivity.class);
                    startActivity(intent);
                }
            });
        }

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int j=0; j<jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            id2 = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            view2 = jsonObject.getInt("view");
                            String date = jsonObject.getString("date");
                            postnum2 = jsonObject.getInt("postnum");
                            adapter2.addPost(new SinglePost(title, id2, date, view2, postnum2));
                            infolist.setAdapter(adapter2);
                            array2.add(j,postnum2);
                        }
                    }

                } catch(JSONException e){
                    e.printStackTrace();
                    /*StringWriter sw = new StringWriter();
                     e.printStackTrace(new PrintWriter(sw));
                     String exceptionAsString = sw.toString();
                     Log.e("StackTrace", exceptionAsString);*/
                }
            }
        };
        ViewInfoBoardRequest viewInfoBoardRequest = new ViewInfoBoardRequest(responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(CommunityActivity.this);
        queue2.add(viewInfoBoardRequest);

        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view2++;
                postnum_2 = array2.get(position);
                Response.Listener<String> responseListener_2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), InfoBoardListView.class);
                                intent.putExtra("postnum",postnum_2);
                                startActivity(intent);
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ModifyInfoViewRequest modifyInfoViewRequest = new ModifyInfoViewRequest(postnum_2, view2, responseListener_2);
                RequestQueue queue_2 = Volley.newRequestQueue(CommunityActivity.this);
                queue_2.add(modifyInfoViewRequest);
            }
        });

        Button write2 = (Button)findViewById(R.id.write2);
        if(!session_id.equals("")){
            write2.setVisibility(View.VISIBLE);
            write2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CommunityActivity.this,WriteInfoActivity.class);
                    startActivity(intent);
                }
            });
        }

        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int z=0; z<jsonArray.length(); z++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(z);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            id3 = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            view3 = jsonObject.getInt("view");
                            String date = jsonObject.getString("date");
                            postnum3 = jsonObject.getInt("postnum");
                            adapter3.addPost(new SinglePost(title, id3, date, view3, postnum3));
                            suggestlist.setAdapter(adapter3);
                            array3.add(z,postnum3);
                        }

                    }
                } catch(JSONException e){
                    e.printStackTrace();
                    /*StringWriter sw = new StringWriter();
                     e.printStackTrace(new PrintWriter(sw));
                     String exceptionAsString = sw.toString();
                     Log.e("StackTrace", exceptionAsString);*/
                }
            }
        };
        ViewSuggestBoardRequest viewSuggestBoardRequest = new ViewSuggestBoardRequest(responseListener3);
        RequestQueue queue3 = Volley.newRequestQueue(CommunityActivity.this);
        queue3.add(viewSuggestBoardRequest);

        suggestlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view3++;
                postnum_3 = array3.get(position);
                Response.Listener<String> responseListener_3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), SuggestBoardListView.class);
                                intent.putExtra("postnum",postnum_3);
                                startActivity(intent);
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ModifySuggestViewRequest modifySuggestViewRequest = new ModifySuggestViewRequest(postnum_3, view3, responseListener_3);
                RequestQueue queue_3 = Volley.newRequestQueue(CommunityActivity.this);
                queue_3.add(modifySuggestViewRequest);
            }
        });

        Button write3 = (Button)findViewById(R.id.write3);
        if(!session_id.equals("")){
            write3.setVisibility(View.VISIBLE);
            write3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CommunityActivity.this,WriteSuggestActivity.class);
                    startActivity(intent);
                }
            });
        }

        Response.Listener<String> responseListener4 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int r=0; r<jsonArray.length(); r++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(r);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            id4 = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            view4 = jsonObject.getInt("view");
                            String date = jsonObject.getString("date");
                            postnum4 = jsonObject.getInt("postnum");
                            adapter4.addPost(new SinglePost(title, id4, date, view4, postnum4));
                            repostlist.setAdapter(adapter4);
                            array4.add(r,postnum4);
                        }
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                    /*StringWriter sw = new StringWriter();
                     e.printStackTrace(new PrintWriter(sw));
                     String exceptionAsString = sw.toString();
                     Log.e("StackTrace", exceptionAsString);*/
                }
            }
        };
        ViewReportBoardRequest viewReportBoardRequest = new ViewReportBoardRequest(responseListener4);
        RequestQueue queue4 = Volley.newRequestQueue(CommunityActivity.this);
        queue4.add(viewReportBoardRequest);

        repostlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view4++;
                postnum_4 = array4.get(position);
                Response.Listener<String> responseListener_4 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), ReportBoardListView.class);
                                intent.putExtra("postnum",postnum_4);
                                startActivity(intent);
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ModifyReportViewRequest modifyReportViewRequest = new ModifyReportViewRequest(postnum_4, view4, responseListener_4);
                RequestQueue queue_4 = Volley.newRequestQueue(CommunityActivity.this);
                queue_4.add(modifyReportViewRequest);
            }
        });

        Button write4 = (Button)findViewById(R.id.write4);
        if(!session_id.equals("")){
            write4.setVisibility(View.VISIBLE);
            write4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CommunityActivity.this,WriteReportActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    class SingleAdapter extends BaseAdapter{
        ArrayList<SinglePost> posts=new ArrayList<SinglePost>();

        @Override
        public int getCount(){
            return posts.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void addPost(SinglePost post){
            posts.add(post);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            SinglePostView singlePostView = null;
            if(convertView==null){
                singlePostView = new SinglePostView(getApplicationContext());
            }else{
                singlePostView = (SinglePostView)convertView;
            }
            SinglePost post=posts.get(position);
            singlePostView.setPostnum(post.getPostnum());
            singlePostView.setWriter(post.getWriter());
            singlePostView.setTitle(post.getTitle());
            singlePostView.setTime(post.getTime());
            singlePostView.setViews(post.getViews());
            return singlePostView;
        }
    }
}
