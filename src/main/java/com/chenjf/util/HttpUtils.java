package com.chenjf.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;


/**
 * Created by chenjf on 2017-09-11.
 */
public class HttpUtils {

    private static final Logger logger = Logger.getLogger(HttpUtils.class);

    public static void sendHttpGet(String mobile) throws IOException {
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_name=guishudi&query=" + mobile + "&_=1505125539063";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(method);
            if(response.getStatusLine().getStatusCode()==200){
                String result = EntityUtils.toString(response.getEntity());
                JSONObject json = new JSONObject();
                JSONObject resJson = json.parseObject(result);
                resJson.get("data");
                String prov = ((JSONObject) ((JSONArray) resJson.get("data")).get(0)).get("prov").toString();
                String city = ((JSONObject) ((JSONArray) resJson.get("data")).get(0)).get("city").toString();
                if ("浙江".equals(prov)) {
                    System.out.print(mobile + ",");
                }
            }
        } catch (Exception e) {

        }
    }

    public static String getExceptionIp(String text) throws IOException {
        if (text.contains("异常IP访问")) {
            return text.split("ip:")[1];
        }
        return null;
    }

    /**
     * 测试宝贝实时排名回调请求
     * @return
     */
    public static String testPost(String mobile){
        HttpClient httpClient = new DefaultHttpClient();
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?cb=jQuery110202753387221626902_1505125539061&resource_name=guishudi&query=" + mobile + "&_=1505125539063";
        HttpPost method = new HttpPost(url);

        String result = "wrong";
        try {
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            //塞入请求参数
//            params.add(new BasicNameValuePair("mobile", mobile));
//            params.add(new BasicNameValuePair("kw", kw));
//            params.add(new BasicNameValuePair("page", page));
//            params.add(new BasicNameValuePair("total", "500"));
//            params.add(new BasicNameValuePair("items", items));
            method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = httpClient.execute(method);
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return result;
    }

    public static String txt2String(File file){
        Set<String> exceptionIpSet = new HashSet<>();
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//                sendHttpGet(s);
                exceptionIpSet.add(getExceptionIp(s));
//                result.append(System.lineSeparator()+s);
            }
            System.out.print(exceptionIpSet);
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 发送http请求
     * @param baseUrl 请求地址
     * @param method (GET || POST)
     * @param params 参数map
     * @return
     */
    public static String sendHttpRequest(String baseUrl, String method, Map<String, Object> params){
        String responseTxt = "";
        HttpURLConnection urlConnection = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(baseUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            out = new DataOutputStream(urlConnection.getOutputStream());
            StringBuffer paramsBuffer = new StringBuffer("");
            if(params != null && params.size() > 0) {
                int index = 0;
                for(Map.Entry<String, Object> entry : params.entrySet()){
                    if((index++)!=0){
                        paramsBuffer.append("&");
                    }
                    String value = "";
                    if(entry.getValue() != null)value = URLEncoder.encode(entry.getValue().toString(), "utf-8");
                    paramsBuffer.append(entry.getKey()).append("=").append(value);
                }
            }
            out.writeBytes(paramsBuffer.toString());
            out.flush();
            out.close();

            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseTxt += line;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                close(reader);
                close(out);
                if(urlConnection != null)
                    urlConnection.disconnect();
            } catch (Exception e) {logger.error(e.getMessage(), e);}
        }
        return responseTxt;
    }

    /**
     * 发送http请求，可设置cookie
     * @param baseUrl 请求地址
     * @param method (GET || POST)
     * @param cookie cookie
     * @param paramString 参数字符串
     * @return
     */
    public static String sendHttpRequestWithCookie(String baseUrl, String method, String cookie, String paramString){
        String responseTxt = "";
        HttpURLConnection urlConnection = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(baseUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            //设置cookie
            if(StringUtils.isNotBlank(cookie)) {
                urlConnection.setRequestProperty("Cookie", cookie);
            }
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            out = new DataOutputStream(urlConnection.getOutputStream());
            out.writeBytes(paramString);
            out.flush();
            out.close();

            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseTxt += line;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                close(reader);
                close(out);
                if(urlConnection != null)
                    urlConnection.disconnect();
            } catch (Exception e) {logger.error(e.getMessage(), e);}
        }
        return responseTxt;
    }

    private static void close(Closeable closeable){
        try{
            if(closeable != null)
                closeable.close();
        }catch (Exception e){logger.error(e.getMessage(), e);}
    }

    public static void main(String[] args) throws IOException {
//        String result = sendHttpRequestWithCookie("http://sycm.taobao.com/qos/excel/reviewOverviewTrendExcel.do?spm=a21ag.8107411.C_review-overview-trend.d65503.3be58a28TIiRM6&dateType=day&dateRange=2017-11-08%7C2017-11-08",
//                    "GET", "", null);
//        System.out.println(11);
//        File file = new File("/Users/chenjf/updown/ip_info.log");
//        txt2String(file);

//        sendHttpGet("13282109725");
//        testPost("13282109725");
//        sendGet("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?cb=jQuery110202753387221626902_1505125539061&resource_name=guishudi&query=15958097092&_=1505125539063");
//        List<String> mobiles = new ArrayList<String>();
//        mobiles.add("13886023693");
//        mobiles.add("13082820106");
//        mobiles.add("18657192781");
//        mobiles.add("13018964161");
//        mobiles.add("18768139471");
//        mobiles.add("13646856280");
//        mobiles.add("18674391429");
//        mobiles.add("13065730349");
//        mobiles.add("15858285624");
//        mobiles.add("15224040070");
//        for (String mobile:mobiles) {
//            sendHttpGet(mobile);
//        }



    }
}
