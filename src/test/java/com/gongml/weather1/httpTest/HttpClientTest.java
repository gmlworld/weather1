package com.gongml.weather1.httpTest;


import com.gongml.weather1.entity.Request;
import com.gongml.weather1.utils.HttpClienUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientTest {

    @Test
    public  void test1(){
        try {
            Request request = new Request();
//            request.setUri("http://www.weather.com.cn/weather/101010100.shtml");
            request.setUri("http://toy1.weather.com.cn/search?cityname=%E9%95%BF%E6%B2%99");
            String s = httpGet(request);
//            WeatherHtmlParser weatherHtmlParser = new WeatherHtmlParser();
//            LifeCodeHtmlParser lifeCodeHtmlParser = new LifeCodeHtmlParser();
//            List<Map<String, Object>> maps1 = lifeCodeHtmlParser.parseTable(s);
//            List<Map<String, Object>> maps = weatherHtmlParser.parseTable(s);
            System.out.println(s);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static  HttpClienUtil httpClienUtil = new HttpClienUtil();

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


}
