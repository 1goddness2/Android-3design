package com.example.chat;

import android.renderscript.ScriptGroup;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {
    static InputStream is = null;
    static String json = "";
    public static String PHPSESSID = null;
    public JSONParser(){

    }
    public String makeHttpRequest(String url, String method){
        Log.println(Log.INFO,"json","方法调用");
        try{
            HttpGet httpGet = new HttpGet(url);
            //HttpPost httpPost = new HttpPost("http://172.20.10.12:8080/login");
            //httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            //System.out.println(new UrlEncodedFormEntity(params));
            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity entity = httpClient.execute(httpGet).getEntity();
            String result = null;
            if (entity != null) {
                System.out.println(entity);
                //System.out.println(entity.getContent());
                //System.out.println(entity.getContentType());
                //System.out.println(entity.getContentEncoding());
                is = entity.getContent();

                System.out.println(is);
            }else {

            }
            //HttpEntity httpEntiy = httpResponse.getEntity();
            //is = httpEntiy.getContent();

        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            is.close();
            json = sb.toString();
            if(!json.endsWith("}")){
                json = json + "}";
            }
            Log.println(Log.INFO,"json",json.toString());
        }catch (Exception e){
            e.printStackTrace();
            Log.println(Log.INFO,"json",json.toString());
        }
        return json;
    }
}
