package com.gongml.weather1.utils;

import com.gongml.weather1.entity.Request;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeatherRequestUtil {

    @Autowired
    private  HttpClienUtil httpClienUtil;
    @Autowired
    private CommonUtils commonUtils;

    private final String prefix="http://sc.ftqq.com/";
    private final String suffix=".send";

    /**
     * @param weatherRequest
     * @return
     * @throws Exception
     * @author wanghui
     * @desc Get请求
     */
    public String httpGet(Request weatherRequest) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        String url = weatherRequest.getUri();
        HttpRequestBase request = httpClienUtil.getHttpGetRequest(url, 60000, null);
        request.setHeader("Referer", url);
        Map<String, String> headers = weatherRequest.getHeaders();
        if (!MapUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> {
                request.setHeader(k, v);
            });
        }
        return httpClienUtil.executeHtmlFor(httpclient, request);
    }

    /**
     * @param weatherRequest
     * @return
     * @throws Exception
     * @author wanghui
     * @desc POST请求
     */
    public String httpPost(Request weatherRequest) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        Map<String, String> headers = weatherRequest.getHeaders();
        String urlStr = weatherRequest.getUri();
        Map<String, String> param = weatherRequest.getParams();
        URI uri = httpClienUtil.getUri(urlStr);
        HttpRequestBase request = httpClienUtil.getHttpPostRequest(uri, 60000, null);
        HttpPost httppost = (HttpPost)  request;
        httppost.setHeader("Referer", urlStr);
        if (!MapUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> {
                httppost.setHeader(k, v);
            });
        }
        if (!MapUtils.isEmpty(param)) {
            if (param.containsKey("RequestBody")) {
                StringEntity stringEntity = new StringEntity(MapUtils.getString(param, "RequestBody"));
                httppost.setEntity(stringEntity);
            } else {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                param.forEach((k, v) -> {
                    list.add(new BasicNameValuePair(k, v));
                });
                // 参数的解码所使用的编码
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
                httppost.setEntity(uefEntity);
            }
        }
        return httpClienUtil.executeHtmlFor(httpclient, httppost);
    }

    public String getHtmlByAreaName(String areaName){
        String areaUrl = commonUtils.getWeatherAreaUrl(areaName);
        return getHtmlByUrl(areaUrl);
    }

    public String getHtmlByAreaNumber(String areaNumber){
        String url = commonUtils.getWeatherUrl(areaNumber);
       return getHtmlByUrl(url);
    }

    public String getHtmlByUrl(String url){
        try {
            Request request = new Request();
            request.setUri(url);
            String html = httpGet(request);
            return html;
        }catch (Exception e){
            return null;
        }
    }

    /** 
    * @Description: 使用Server酱的微信推送接口  发送消息提醒  
    * @Param: [url, text, desp]
    * @return: java.lang.String 
    * @Author: Gongml
    * @Date: 2018-08-08 
    */ 
    public String sendInfoToWx(String sckey,String text,String desp){
        try {
            Request request = new Request();
            String url = prefix+sckey+suffix;
            request.setUri(url);
            Map<String, String> paramMap = new HashMap<>(4);
            paramMap.put("text",text);
            paramMap.put("desp",desp);
            request.setParams(paramMap);
            String html = httpPost(request);
            return html;
        }catch (Exception e){
            return null;
        }
    }
}
