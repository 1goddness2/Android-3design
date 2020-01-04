package com.example.chat.ui.notifications;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chat.Chat;
import com.example.chat.ImageUpload;
import com.example.chat.JSONParser;
import com.example.chat.Publish;
import com.example.chat.QqMainActivity;
import com.example.chat.R;
import com.example.chat.ui.dashboard.DashboardFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.SyncFailedException;

public class NotificationsFragment extends Fragment {
    private Button loginBt;
    private Button  registerBt;
    private EditText qqNo, qqPsw;
    private ProgressDialog pDialog;
    JSONParser jsonParser= new JSONParser();
    private String jsonData;
    private String message;
    private int success;
    public static String user_name;
    public static String BaseUPL = "";     //服务器url
    int i = 0;
    Button button_;
    private static String url_login = "http://172.20.10.12:5000/kongjian";
    View root;
    LayoutInflater li;
    ViewGroup vg;
    Bundle b;
    LinearLayout linearLayout;
    ImageButton ib;
    EditText editT;
    private static final int COMPLETED = 0;
    private static final int COMPLETE = 1;
    private NotificationsViewModel notificationsViewModel;
    EditText etListLibraryNote;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        li = inflater;
        vg = container;
        b = savedInstanceState;
        button_ = root.findViewById(R.id.button_search);
        editT = root.findViewById(R.id.input_textsearch);
        linearLayout = root.findViewById(R.id.llt2);
        new Login().execute();
        ib = root.findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现一个可以跳转界面的实例
                Intent myin = new Intent(getActivity(), Publish.class);
                //开启意图，并设置请求码是0，相当于设置一个监听或者中断
                //这个中断将在运行到setResult(结果码,意图实例);这样的代码返回来
                //请求码用于判定返回的意图传到哪里，
                startActivity(myin);

            }
        });
        button_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现一个可以跳转界面的实例
                String strs = editT.getText().toString();
                if (strs == ""){

                }else {
                    new Search().execute(strs);
                }


            }
        });

        return root;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                try {
                    JSONObject jsono = new JSONObject(jsonData);
                    JSONArray jArr = jsono.getJSONArray("data");
                    System.out.println(jArr);
                    for (int i = 0; i < jArr.length(); i++) {
                        JSONObject obj2 = jArr.getJSONObject(i);
                        String str1 = obj2.getString("name");
                        String str2 = obj2.getString("contentId");
                        String str4 = obj2.getString("content");
                        final String str3 = obj2.getString("man");
                        String str7 = obj2.getString("use_name");
                        String str6 = obj2.getString("t");
                        JSONArray jArr1 = obj2.getJSONArray("comm");
                        set(li, vg, b, linearLayout, str1,str2,str3,str4,jArr1,str6,str7);
                        System.out.println(str1);
                        System.out.println(jArr1);
                    } //UI更改操作
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if (msg.what == COMPLETE) {
                try {

                    JSONObject jsono = new JSONObject(jsonData);
                    JSONArray jArr = jsono.getJSONArray("data");
                    System.out.println(jArr);
                    for (int i = 0; i < jArr.length(); i++) {
                        JSONObject obj2 = jArr.getJSONObject(i);
                        String str1 = obj2.getString("name");
                        String str2 = obj2.getString("contentId");
                        String str4 = obj2.getString("content");
                        final String str3 = obj2.getString("man");
                        String str6 = obj2.getString("t");
                        String str7 = obj2.getString("use_name");
                        JSONArray jArr1 = obj2.getJSONArray("comm");
                        sets(li, vg, b, linearLayout, str1,str2,str3,str4,jArr1,str6,str7);
                        System.out.println(str1);
                        System.out.println(jArr1);
                    } //UI更改操作
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public void sets(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, LinearLayout linearLayout, String str1, final String str2, final String str3, String str4,JSONArray jArr,String str6,final String str7){
        View root1 = inflater.inflate(R.layout.pyq, container, false);
        final LinearLayout llt = root1.findViewById(R.id.combineCtrlc);
        final ImageView iv= root1.findViewById(R.id.imgp);
        linearLayout.removeAllViews();
        if(str6.equals("1")) iv.setBackgroundResource(R.mipmap.chenweiting);
        else if (str6.equals("2")) iv.setBackgroundResource(R.mipmap.luguangzhu);
        else if (str6.equals("3")) iv.setBackgroundResource(R.mipmap.ouyangnana);
        else if (str6.equals("4")) iv.setBackgroundResource(R.mipmap.pengyuyan);
        else if (str6.equals("5")) iv.setBackgroundResource(R.mipmap.wuyanzu);
        else if (str6.equals("6")) iv.setBackgroundResource(R.mipmap.lisa);
        final EditText edit1 = root1.findViewById(R.id.namep);
        edit1.setText(str1);
        final EditText edit2 = root1.findViewById(R.id.pyqcontent);
        edit2.setText(str4);
        final Button button = root1.findViewById(R.id.buttonc);
        try {

            i += 1;
            System.out.println("第" + i +"次" + jArr);
            for (int j = 0; j < jArr.length(); j++) {
                JSONObject obj3 = jArr.getJSONObject(j);
                String str11 = obj3.getString("manname");
                String str12 = obj3.getString("ccomment");
                final String str13 = obj3.getString("comman");
                System.out.println(str13);
                View root11 = inflater.inflate(R.layout.comment, container, false);
                final EditText edit11 = root11.findViewById(R.id.nameccu);
                edit11.setText(str11);
                final EditText edit21 = root11.findViewById(R.id.namecc);
                edit21.setText(str12);
                System.out.println(str12);
                root11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("是否添加好友");

                        builder.setPositiveButton("确定",null);
                        builder.setNegativeButton("取消",null);
                        builder.create();
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                System.out.println("加好友" + str13);
                                new GetFriend().execute(str13);
                                dialog.dismiss();
                            }
                        });

                    }
                });
                llt.addView(root11);
            }}catch(Exception e){
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //verifyStoragePermissions(MainActivity.this);
                final View layout = View.inflate(getActivity(), R.layout.dialog,
                        null);
                final EditText inputServer = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入评论");
                builder.setView(layout);
                builder.setPositiveButton("发表",null);
                builder.setNegativeButton("取消",null);
                builder.create();
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        etListLibraryNote = (EditText) layout.findViewById(R.id.et_list_library_note);
                        System.out.println(inputServer.getContext().toString());
                        String libraryNote = etListLibraryNote.getText().toString();
                        View view=LayoutInflater.from(getActivity()).inflate(
                                R.layout.comment, null);
                        final EditText edit11 = view.findViewById(R.id.nameccu);
                        edit11.setText(str7);
                        final EditText edit21 = view.findViewById(R.id.namecc);
                        edit21.setText(libraryNote);
                        System.out.println(libraryNote);
                        llt.addView(view);
                        new Comment().execute(str2,libraryNote);
                        dialog.dismiss();
                    }
                });
            }
        });
        linearLayout.addView(root1);
        System.out.println("调用结束");
    }

    public void setc(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, LinearLayout linearLayout, String str1, final String str2, final String str3){
        View root1 = inflater.inflate(R.layout.comment, container, false);
        final EditText edit1 = root1.findViewById(R.id.nameccu);
        edit1.setText(str1);
        final EditText edit2 = root1.findViewById(R.id.namecc);
        edit2.setText(str2);
        linearLayout.addView(root1);
    }

    public void set(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, LinearLayout linearLayout, String str1, final String str2, final String str3, String str4,JSONArray jArr,String str6,final String str7){
        View root1 = inflater.inflate(R.layout.pyq, container, false);
        final LinearLayout llt = root1.findViewById(R.id.combineCtrlc);
        final ImageView iv= root1.findViewById(R.id.imgp);
        if(str6.equals("1")) iv.setBackgroundResource(R.mipmap.chenweiting);
        else if (str6.equals("2")) iv.setBackgroundResource(R.mipmap.luguangzhu);
        else if (str6.equals("3")) iv.setBackgroundResource(R.mipmap.ouyangnana);
        else if (str6.equals("4")) iv.setBackgroundResource(R.mipmap.pengyuyan);
        else if (str6.equals("5")) iv.setBackgroundResource(R.mipmap.wuyanzu);
        else if (str6.equals("6")) iv.setBackgroundResource(R.mipmap.lisa);
        final EditText edit1 = root1.findViewById(R.id.namep);
        edit1.setText(str1);
        edit1.setFocusableInTouchMode(false);
        final EditText edit2 = root1.findViewById(R.id.pyqcontent);
        edit2.setText(str4);
        edit2.setFocusableInTouchMode(false);
        final Button button = root1.findViewById(R.id.buttonc);
        try {

            i += 1;
            System.out.println("第" + i +"次" + jArr);
        for (int j = 0; j < jArr.length(); j++) {
            JSONObject obj3 = jArr.getJSONObject(j);
            String str11 = obj3.getString("manname");
            String str12 = obj3.getString("ccomment");
            final String str13 = obj3.getString("comman");
            System.out.println(str13);
            View root11 = inflater.inflate(R.layout.comment, container, false);
            final EditText edit11 = root11.findViewById(R.id.nameccu);
            edit11.setText(str11);
            edit11.setFocusableInTouchMode(false);
            final EditText edit21 = root11.findViewById(R.id.namecc);
            edit21.setText(str12);
            edit21.setFocusableInTouchMode(false);
            System.out.println(str12);
            root11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("是否添加好友");

                    builder.setPositiveButton("确定",null);
                    builder.setNegativeButton("取消",null);
                    builder.create();
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            System.out.println("加好友" + str13);
                            new GetFriend().execute(str13);
                            dialog.dismiss();
                        }
                    });

                }
            });
            llt.addView(root11);
        }}catch(Exception e){
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //verifyStoragePermissions(MainActivity.this);
                final View layout = View.inflate(getActivity(), R.layout.dialog,
                        null);
                final EditText inputServer = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入评论");
                builder.setView(layout);
                builder.setPositiveButton("发表",null);
                builder.setNegativeButton("取消",null);
                builder.create();
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        etListLibraryNote = (EditText) layout.findViewById(R.id.et_list_library_note);
                        System.out.println(inputServer.getContext().toString());
                        String libraryNote = etListLibraryNote.getText().toString();
                        View view=LayoutInflater.from(getActivity()).inflate(
                                R.layout.comment, null);
                        final EditText edit11 = view.findViewById(R.id.nameccu);
                        edit11.setText(str7);
                        final EditText edit21 = view.findViewById(R.id.namecc);
                        edit21.setText(libraryNote);
                        System.out.println(libraryNote);
                        llt.addView(view);
                        new Comment().execute(str2,libraryNote);
                        dialog.dismiss();
                    }
                });
            }
        });
        linearLayout.addView(root1);
        System.out.println("调用结束");
    }
    class Search extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){
            try{
                jsonData = jsonParser.makeHttpRequest("http://172.20.10.12:5000/search?scontent=" + args[0],"POST");
                try{

                    Message message = new Message();
                    message.what = COMPLETE;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
    class Login extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){
            try{
                jsonData = jsonParser.makeHttpRequest(url_login,"POST");
                try{
                    Message message = new Message();
                    message.what = COMPLETED;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
    class GetFriend extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){
            try{
                String url = "http://172.20.10.12:5000/friend?comman=" + args[0];
                System.out.println(url);
                jsonData = jsonParser.makeHttpRequest(url,"POST");

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
    class Comment extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){
            try{
                String url = "http://172.20.10.12:5000/comment?contentId=" + args[0] + "&content=" + args[1];
                jsonData = jsonParser.makeHttpRequest(url,"POST");

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
}