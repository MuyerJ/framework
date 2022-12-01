package com.muyer.spider;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Description: <br/>
 * date: 2020/7/15 23:08<br/>
 *
 * @author MuyerJ<br />
 */
public class Request {
    /**  HttpClient生成器*/
    private HttpClientBuilder httpClientBuilder = HttpClients.custom();

    /** 请求参数生成器*/
    private RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

    /** 默认编码 ： utf-8*/
    private String charSet = "UTF-8";

    /** HttpResponse的text*/
    private String content;

    /** 其他参数列表,用于在回调函数之间传递数据 */
    private Map<String, Object> packageMap = new HashMap<>();

    /** response的状态码*/
    private int statusCode;

    /** 响应头*/
    private Map<String, String> responseHeadersMap = new HashMap<>();

    /** 请求头*/
    private Map<String, String> requestHeadersMap = new HashMap<>();

    /** Cookie*/
    private Map<String, String> cookiesMap = new HashMap<>();
    private BasicCookieStore cookieStore = new BasicCookieStore();

    /** 爬虫代理*/
    private Proxy proxy = null;

    private CredentialsProvider credsProvider = null;

    private String url;

    private String errorDescription = "";

    /** 请求类型：get和post*/
    private String requestType;

    private Map postMap;

    private boolean parameterIsJsonStr;

    private String jsonStr = "";

    private String parseContent = "";

    /** 构造器*/
    public Request() {
        new Request(5);
    }

    public Request(int retryTime) {
        requestConfigBuilder.setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(10000);
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", plainsf)
                        .register("https", sslsf).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        connectionManager.setMaxTotal(100);
        httpClientBuilder.setConnectionManager(connectionManager);
    }

    public Request setTime(int connectionRequestTime, int connectionTime, int socketTime) {
        requestConfigBuilder
                .setConnectionRequestTimeout(connectionRequestTime)
                .setConnectTimeout(connectionTime)
                .setSocketTimeout(socketTime);
        return this;
    }

    /**
     * @param url
     * @return
     */
    public Request get(String url) {
        CallBack callBack = null;
        return get(url, callBack);
    }

    public Request get(String url, CallBack callBack) {
        return get(url, callBack, 60);
    }

    public Request get(String url, int timeOut) {
        return get(url, null, 60);
    }


    public Request get(String url, CallBack callBack, int timeOut) {
        Runnable task = () -> rawGet(url, callBack);
        //线程池执行请求
        future(task);
        return this;
    }



    protected Request rawGet(String url, CallBack callBack) {
        //设置请求类型为get
        this.requestType = "get";

        HttpGet httpGet = new HttpGet(url);

        //检测是否使用了代理
        if (this.proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(this.proxy.getHost(), this.proxy.getPort()));
        }

        //请求参数配置
        RequestConfig requestConfig = requestConfigBuilder.build();

        //请求cookie配置
        RequestConfig cookieConfig = RequestConfig.copy(requestConfig)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .build();
        httpGet.setConfig(cookieConfig);

        //设置请求头
        httpGet.setHeaders(transRequestHeaderMapToHeaderArray(this.requestHeadersMap));

        //代理认证
        if (this.proxy != null && this.proxy.getUserName() != null) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.proxy.getUserName(), this.proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }
        //请求
        CloseableHttpClient httpclient = httpClientBuilder.setDefaultCookieStore(this.cookieStore).build();
        try {
            execute(url, httpGet, httpclient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //回调
        if (callBack != null) {
            callBack.func(this);
        }
        return this;
    }

    public void execute(String url, HttpGet httpGet, CloseableHttpClient httpClient) throws IOException {
        this.url = url;
        CloseableHttpResponse response = httpClient.execute(httpGet);
        formCookiesMap();
        this.content = EntityUtils.toString(response.getEntity(), this.charSet);
        formResponseHeadersMap(response.getAllHeaders());
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    private void formCookiesMap() {
        this.cookieStore.getCookies().forEach(k -> {
            this.cookiesMap.put(k.getName(), k.getValue());
        });
    }

    /**
     * 弃用,改使用setCharSet setHeadersMap setProxy的方式设置
     */
    @Deprecated
    public Request post(String url, Map<String, String> param, Proxy proxy, Map<String, String> headersMap) {
        this.proxy = proxy;
        this.requestHeadersMap = headersMap;
        return post(url, param, null);
    }

    public Request post(String url, Map<String, String> param, CallBack callBack) {
        Runnable task = () -> rawPost(url, param, callBack);
        future(task);
        return this;
    }

    private void future(Runnable task) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(task);
        try {
            future.get(45, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println(Thread.currentThread().getName() + " 超时45秒");
        }
    }

    private Request rawPost(String url, Map<String, String> param, CallBack callBack) {
        this.postMap = param;
        this.requestType = "post";
        this.url = url;
        HttpPost post = new HttpPost(url);

        if (this.proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(this.proxy.getHost(), this.proxy.getPort()));
        }
        RequestConfig requestConfig = requestConfigBuilder.build();
        post.setConfig(requestConfig);

        post.setHeaders(transRequestHeaderMapToHeaderArray(this.requestHeadersMap));
        //封装提交到服务器的参数信息
        List<NameValuePair> list = new ArrayList<>();
        param.keySet().forEach(k -> list.add((new BasicNameValuePair(k, param.get(k)))));
        //TODO Stringutils 判断为空
        if (parameterIsJsonStr && jsonStr!=null) {
            try {
                post.setEntity(new StringEntity(jsonStr));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                parameterIsJsonStr = false;
            }
        } else {
            //表单参数编码格式为utf8
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, Consts.UTF_8);
            //设置参数信息
            post.setEntity(formEntity);
        }
        //提交post方法
        if (this.cookieStore.getCookies().size() > 0) {
            httpClientBuilder.setDefaultCookieStore(this.cookieStore);
        }
        if (this.proxy != null && this.proxy.getUserName() != null) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.proxy.getUserName(), this.proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }
        try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
            execute(url, post, httpclient);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if (callBack != null) {
            callBack.func(this);
        }
        return this;
    }

    private void execute(String url, HttpPost post, CloseableHttpClient httpclient) throws IOException {
        CloseableHttpResponse response = httpclient.execute(post);
        formResponseHeadersMap(response.getAllHeaders());
        formCookiesMap();
        this.content = EntityUtils.toString(response.getEntity(), this.charSet);
        this.url = url;
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    /**
     * @param url
     * @param param
     * @return
     */
    public Request post(String url, Map<String, String> param) {
        return post(url, param, null);
    }

    /**
     * 参数为json字符串格式时通过jsonStr传参而不需要postMap
     *
     * @param url
     * @return
     */
    public Request post(String url) {
        parameterIsJsonStr = true;
        return post(url, new HashMap<>(), null);
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }


    public Map<String, String> getRequestHeadersMap() {
        return requestHeadersMap;
    }

    public Request setRequestHeadersMap(Map<String, String> map) {
        this.requestHeadersMap = new HashMap<>(map);
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map getPostMap() {
        return postMap;
    }

    public void setPostMap(Map postMap) {
        this.postMap = postMap;
    }

    public void addRequestHeader(String name, String value) {
        this.requestHeadersMap.put(name, value);
    }


    public void removeRequestHeader(String name) {
        this.requestHeadersMap.remove(name);
    }

    public Map<String, Object> getPackageMap() {
        return packageMap;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getResponseHeadersMap() {
        return responseHeadersMap;
    }

    public Request setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public Request addPackageParam(String name, Object value) {
        packageMap.put(name, value);
        return this;
    }

    public Object getPackageParam(String name) {
        return packageMap.get(name);
    }

    private void formResponseHeadersMap(Header[] headers) {
        Arrays.stream(headers).
                forEach(k -> responseHeadersMap.put(k.getName(), k.getValue()));
    }

    private List<Header> transRequestHeaderMapToHeaderList(Map<String, String> requestHeadersMap) {
        return requestHeadersMap.keySet().stream()
                .map(k -> new BasicHeader(k, requestHeadersMap.get(k)))
                .collect(Collectors.toList());
    }

    private Header[] transRequestHeaderMapToHeaderArray(Map<String, String> requestHeadersMap) {
        return requestHeadersMap.keySet().stream()
                .map(k -> new BasicHeader(k, requestHeadersMap.get(k)))
                .toArray(Header[]::new);
    }

    public BasicCookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(BasicCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getCharSet() {
        return charSet;
    }

    public boolean isParameterIsJsonStr() {
        return parameterIsJsonStr;
    }

    public Request setParameterIsJsonStr(boolean parameterIsJsonStr) {
        this.parameterIsJsonStr = parameterIsJsonStr;
        return this;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public Request setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
        return this;
    }

    public Request addCookieFromCookieMapToHeaders() {
        String cookie = this.cookiesMap.keySet().stream().map(k -> k + "=" + cookiesMap.get(k)).collect(Collectors.joining(";"));
        this.getRequestHeadersMap().put("Cookie", cookie);
        return this;
    }

    public String getParseContent() {
        return parseContent;
    }

    public void setParseContent(String parseContent) {
        this.parseContent = parseContent;
    }

    private void retry(long time) throws Exception {
//        if (getStatusCode() == HTTP_STATE_CODE_OK) {
//            TimeUnit.SECONDS.sleep(time);
//            return;
//        }
//
//        while (getStatusCode() != HTTP_STATE_CODE_OK) {
//            boolean isGet = postMap == null;
//            if (isGet) {
//                this.useDefaultProxy().get(this.getUrl());
//            } else {
//                this.useDefaultProxy().post(this.getUrl(), this.getPostMap());
//            }
//            TimeUnit.SECONDS.sleep(time);
//            System.out.println("重试中 ");
//        }
    }

    public Request retryRequest(long time) {
        boolean retry = true;
        while (retry) {
            try {
                retry = false;
                retry(time);
            } catch (Exception e) {
                retry = true;
            }
        }
        return this;
    }

    public Request retryRequest() {
        return retryRequest(1);
    }

    public Request setCharSet(String charSet) {
        this.charSet = charSet;
        return this;
    }

    public Request setContent(String content) {
        this.content = content;
        return this;
    }

    public Request setPackageMap(Map<String, Object> packageMap) {
        this.packageMap = packageMap;
        return this;
    }

    public Request setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Request setResponseHeadersMap(Map<String, String> responseHeadersMap) {
        this.responseHeadersMap = responseHeadersMap;
        return this;
    }

    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }

    public Request setCookiesMap(Map<String, String> cookiesMap) {
        this.cookiesMap = cookiesMap;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public CredentialsProvider getCredsProvider() {
        return credsProvider;
    }

    public Request setCredsProvider(CredentialsProvider credsProvider) {
        this.credsProvider = credsProvider;
        return this;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Request setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }
}