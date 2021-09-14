package com.example.hunminjungum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportBoardListView extends Activity {
    TextView rt_title, rt_writer, rt_date, rt_view, rt_content;
    Button delete, modify;
    String session_id, id;
    int postnum1, postnum2, commentnum, commentnum_3;
    EditText user_comment;
    TextView comment, comment1;
    EditText comment2;
    Button bt_comment;
    String s_comment, userID;
    ListView commentlist;
    CommentAdapter adapter;
    ArrayList<Integer> array = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_post);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        session_id = sharedPreferences.getString("id", "");

        rt_title = (TextView) findViewById(R.id.txt_title);
        rt_writer = (TextView) findViewById(R.id.txt_writer);
        rt_date = (TextView) findViewById(R.id.txt_date);
        rt_view = (TextView) findViewById(R.id.txt_view2);
        rt_content = (TextView) findViewById(R.id.txt_content);
        delete = (Button) findViewById(R.id.delete);
        modify = (Button) findViewById(R.id.modify);
        commentlist = (ListView)findViewById(R.id.commentlist);
        adapter = new CommentAdapter();

        Intent intent = getIntent();
        postnum1 = intent.getExtras().getInt("postnum");

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        int view = jsonObject.getInt("view");
                        String date = jsonObject.getString("date");
                        String content = jsonObject.getString("content");
                        postnum2 = jsonObject.getInt("postnum");

                        rt_title.setText(title);
                        rt_writer.setText(id);
                        rt_date.setText(date);
                        rt_view.setText(String.valueOf(view));
                        rt_content.setText(content);

                        if(!session_id.equals("") && session_id.equals(id)){
                            delete.setVisibility(View.VISIBLE);
                            modify.setVisibility(View.VISIBLE);
                            delete.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    DialogInterface.OnClickListener yesButtonClickListener= new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                                @Override
                                                public void onResponse(String response) {
                                                    try{
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if(success){
                                                            Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                    catch(Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            DeleteReportBoardRequest deleteReportBoardRequest = new DeleteReportBoardRequest(postnum2, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(ReportBoardListView.this);
                                            queue.add(deleteReportBoardRequest);
                                        }
                                    };
                                    AlertDialog.Builder builder1=new AlertDialog.Builder( ReportBoardListView.this );
                                    builder1.setMessage("글을 삭제하시겠습니까?")
                                            .setPositiveButton("확인",yesButtonClickListener)
                                            .create()
                                            .show();
                                }
                            });

                            modify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent mintent = new Intent(ReportBoardListView.this, ModifyReportBoard.class);
                                    mintent.putExtra("postnum",postnum2);
                                    startActivity(mintent);
                                }
                            });
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
        ReportBoardListViewRequest reportBoardListViewRequest = new ReportBoardListViewRequest(postnum1, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(ReportBoardListView.this);
        queue1.add(reportBoardListViewRequest);

        comment = (TextView)findViewById(R.id.comment1);
        user_comment = (EditText)findViewById(R.id.comment2);
        bt_comment = (Button)findViewById(R.id.comment_ok) ;
        if(!session_id.equals("")){
            comment.setVisibility(View.VISIBLE);
            user_comment.setVisibility(View.VISIBLE);
            bt_comment.setVisibility(View.VISIBLE);
            bt_comment.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    s_comment = user_comment.getText().toString();
                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(),"댓글 작성을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportBoardListView.this);
                                    builder.setMessage("댓글 작성을 완료하였습니다.")
                                            .setPositiveButton("ok", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(ReportBoardListView.this, CommunityActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(),"댓글 작성에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportBoardListView.this);
                                    builder.setMessage("댓글 작성에 실패하였습니다.")
                                            .setNegativeButton("ok", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    CommentRequest suggestCommentRequest = new CommentRequest(session_id, postnum2, 3, s_comment, responseListener2);
                    RequestQueue queue2 = Volley.newRequestQueue(ReportBoardListView.this);
                    queue2.add(suggestCommentRequest);
                }
            });
        }

        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            userID = jsonObject.getString("id");
                            final String userComment = jsonObject.getString("content");
                            commentnum = jsonObject.getInt("commentnum");
                            adapter.addPost(new SingleComment(userID, userComment));
                            commentlist.setAdapter(adapter);
                            array.add(i, commentnum);
                            if (session_id.equals(userID)) {
                                commentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        commentnum_3 = array.get(position);
                                        DialogInterface.OnClickListener yesButtonClickListener2 = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Response.Listener<String> responseListener5 = new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if (success) {
                                                                Toast.makeText(getApplicationContext(), "댓글 삭제를 완료했습니다.", Toast.LENGTH_SHORT).show();
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(ReportBoardListView.this);
                                                                builder.setMessage("댓글 삭제를 완료했습니다.")
                                                                        .setPositiveButton("ok", null)
                                                                        .create();
                                                                Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(commentnum,session_id, responseListener5);
                                                RequestQueue queue5 = Volley.newRequestQueue(ReportBoardListView.this);
                                                queue5.add(deleteCommentRequest);
                                            }
                                        };
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ReportBoardListView.this);
                                        builder1.setMessage("댓글을 삭제하시겠습니까?")
                                                .setPositiveButton("확인", yesButtonClickListener2)
                                                .create()
                                                .show();
                                    }
                                });
                            }
                        } else {

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ViewCommentRequest viewSuggestCommentRequest = new ViewCommentRequest(postnum1, 3, responseListener3);
        RequestQueue queue3 = Volley.newRequestQueue(ReportBoardListView.this);
        queue3.add(viewSuggestCommentRequest);
    }
    class CommentAdapter extends BaseAdapter {
        ArrayList<SingleComment> comments=new ArrayList<SingleComment>();

        @Override
        public int getCount(){
            return comments.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void addPost(SingleComment comment){
            comments.add(comment);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            SingleCommentView singleCommentView = null;
            if(convertView==null){
                singleCommentView = new SingleCommentView(getApplicationContext());
            }else{
                singleCommentView = (SingleCommentView)convertView;
            }
            SingleComment comment=comments.get(position);
            singleCommentView.setComment(comment.getComment());
            singleCommentView.setWriter(comment.getWriter());
            return singleCommentView;
        }
    }
}
