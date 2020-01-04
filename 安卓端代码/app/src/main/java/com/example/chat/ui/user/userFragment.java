package com.example.chat.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chat.Chat;
import com.example.chat.JSONParser;
import com.example.chat.MainActivity;
import com.example.chat.Publish;
import com.example.chat.QqMainActivity;
import com.example.chat.R;


import org.json.JSONArray;
import org.json.JSONObject;

public class userFragment extends Fragment {

    private userViewModel userViewModel;
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
    private static String url_login = "http://172.20.10.12:5000/user";

    LayoutInflater li;
    ViewGroup vg;
    Bundle b;

    LinearLayout linearLayout;
    private static final int COMPLETED = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(userViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        li = inflater;
        vg = container;
        b = savedInstanceState;

        linearLayout = root.findViewById(R.id.text_user);
        new Login().execute();
        return root;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                try {
                    JSONObject jsono = new JSONObject(jsonData);
                    System.out.println(jsono);
                    JSONArray jArr = jsono.getJSONArray("data");

                        JSONObject obj2 = jArr.getJSONObject(0);
                        String str1 = obj2.getString("userName");
                        final String str3 = obj2.getString("touxiang");
                        System.out.println(str1);
                        set(li, vg, b, str1,str3);

                    //String str1 = obj2.getString("userName");
                    //String str2 = obj2.getString("record");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public void set(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState,String str1,String str2){
        View root1 = inflater.inflate(R.layout.user, container, false);
        final ImageView iv = root1.findViewById(R.id.imageViewu);
        if(str2.equals("1")) iv.setBackgroundResource(R.mipmap.chenweiting);
        else if (str2.equals("2")) iv.setBackgroundResource(R.mipmap.luguangzhu);
        else if (str2.equals("3")) iv.setBackgroundResource(R.mipmap.ouyangnana);
        else if (str2.equals("4")) iv.setBackgroundResource(R.mipmap.pengyuyan);
        else if (str2.equals("5")) iv.setBackgroundResource(R.mipmap.wuyanzu);
        else if (str2.equals("6")) iv.setBackgroundResource(R.mipmap.lisa);
        else System.out.println(str2);
        final TextView tv = root1.findViewById(R.id.textViewu);
        tv.setText(str1);
        tv.setFocusableInTouchMode(false);
        Button buttonuf = root1.findViewById(R.id.buttonu);
        buttonuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myin = new Intent(getActivity(), MainActivity.class);

                startActivity(myin);
            }
        });
        linearLayout.addView(root1);
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
}