package com.gongml.weather1.utils;

import com.gongml.weather1.entity.FileByte;
import com.gongml.weather1.entity.ProxyHost;
import org.apache.commons.lang.CharSet;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Component
public class HttpClienUtil {
    /**
     * @param httpRequestBase
     * @author wanghui
     * @desc 设置默认header
     */
    public void setDefaultHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Accept-Encoding", "gzip, deflate");
        httpRequestBase.setHeader("Accept-Language", "en-US,en;q=0.8");
        httpRequestBase.setHeader("Connection", "keep-alive");
        httpRequestBase.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)Chrome/57.0.2987.133 Safari/537.36");
    }

    public URI getUri(String urlStr) throws Exception {
        int index = urlStr.indexOf('?');
        URI uri = null;
        try {
            uri = URI.create(urlStr);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException && index != -1) {
                String hostUrl = urlStr.substring(0, index + 1);
                String params = urlStr.substring(index + 1);
                params = URLEncoder.encode(params, "UTF-8");
                uri = URI.create(hostUrl + params);
            } else {
                throw e;
            }
        }
        return uri;
    }

    public FileByte httpDownFile(String resourceUrl, int timeOut, ProxyHost proxyHost) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpRequestBase request = getHttpGetRequest(resourceUrl, timeOut, proxyHost);
        return executeFileByteFor(httpclient, request);
    }

    public Boolean linkIsFile(String resourceUrl, ProxyHost proxyHost) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpRequestBase request = getHttpGetRequest(resourceUrl, 10000, proxyHost);
        return executeBooleanFor(httpclient, request);
    }

    public String executeHtmlFor(HttpClient httpclient, HttpRequestBase request) throws Exception {
        return this.executeHtmlFor(httpclient, request, null);
    }

    public String executeHtmlFor(HttpClient httpclient, HttpRequestBase request, String charsetName) throws Exception {
        ResponseHandler<String> rh = response -> {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            if (charset == null && charsetName != null) {
                charset = Charset.forName(charsetName);
            } else if (charset == null && charsetName == null) {
                charset = Charset.forName("UTF-8");
            }
            return EntityUtils.toString(entity, charset);
        };
        return httpclient.execute(request, rh);
    }

    public Boolean executeBooleanFor(HttpClient httpclient, HttpRequestBase request) throws Exception {
        ResponseHandler<Boolean> rh = response -> {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            ContentType contentType = ContentType.getOrDefault(entity);
            String contentTypeStr = contentType == null ? "" : contentType.toString();
            if (contentTypeStr.contains("text/html")) {
                return true;
            }
            return false;
        };
        return httpclient.execute(request, rh);
    }


    public FileByte executeFileByteFor(HttpClient httpclient, HttpRequestBase request) throws Exception {
        ResponseHandler<FileByte> rh = response -> {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            FileByte fileByte = new FileByte();
            ContentType contentType = ContentType.getOrDefault(entity);
            Header firstHeader = response.getFirstHeader("Content-Disposition");
            fileByte.setContent(EntityUtils.toByteArray(entity));
            fileByte.setContentType(contentType == null ? "" : contentType.toString());
            fileByte.setContentDisposition(firstHeader == null ? "" : firstHeader.getValue());
            return fileByte;
        };
        return httpclient.execute(request, rh);
    }

    public void enableProxy(HttpRequestBase request, Builder builder, ProxyHost proxyHost) {
        HttpHost proxy = new HttpHost(proxyHost.getIp(), proxyHost.getPort());
        builder.setProxy(proxy);
    }

    public RequestConfig getRequestConfig(HttpRequestBase request, int timeOut, ProxyHost proxyHost) {
        Builder builder = RequestConfig.custom().setSocketTimeout(timeOut).setRedirectsEnabled(true)
                .setCircularRedirectsAllowed(true).setMaxRedirects(20);
        if (proxyHost != null) {
            enableProxy(request, builder, proxyHost);
        }
        return builder.build();
    }


    public HttpRequestBase getHttpGetRequest(String url, int timeOut, ProxyHost proxyHost) {
        HttpGet httpget = new HttpGet(url);
        initRequestBase(httpget, timeOut, proxyHost);
        return httpget;
    }

    public HttpRequestBase getHttpPostRequest(URI uri, int timeOut, ProxyHost proxyHost) {
        HttpPost httpPost = new HttpPost(uri);
        initRequestBase(httpPost, timeOut, proxyHost);
        return httpPost;
    }


    public HttpRequestBase initRequestBase(HttpRequestBase requestBase, int timeOut, ProxyHost proxyHost) {
        RequestConfig config = getRequestConfig(requestBase, timeOut, proxyHost);
        requestBase.setConfig(config);
        setDefaultHeader(requestBase);
        return requestBase;
    }
}
