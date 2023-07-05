package com.example.demo.selenium.util;

import com.baidu.aip.ocr.AipOcr;
import com.example.demo.selenium.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: WebDriverUtils
 * @Description:
 * @Author lianyixiong
 * @Date 2023/7/4
 * @Version 1.0
 */
public class WebDriverUtils {
    // 设置等待时间，单位为毫秒
    private static final long SLEEP_TIME = 1000;
    /******************短信服务************************/
    //抢到场地后接收短信的手机号码
    private static String sms_phone_ = "";
    //短信内容变量需包含哪些内容信息
    private static String local_area_ = "PETS";
    private static String smsSignId = "";
    private static String templateId = "";
    private static String appcode = "";

    public static String getText(String logs, WebDriver webDriver, String url){
        return findElement(logs,webDriver,url).getText();
    }


    public static List<WebElement> getElements(String logs, WebDriver webDriver, String url, String cssSelector)  {
        return findElement(logs,webDriver,url).findElements(By.cssSelector(cssSelector));
    }

    public static void click(String logs,WebDriver webDriver,String url)  {
        findElement( logs, webDriver, url).click();
        waitForSleep();
    }

    public static void sendKeys(String logs, WebDriver webDriver, String url, CharSequence... var1)  {
        findElement( logs, webDriver, url).sendKeys(var1);
        waitForSleep();
    }

    public static WebElement findElement(String logs,WebDriver webDriver,String url)  {
        System.out.println(logs);
        WebElement we =webDriver.findElement(By.xpath(url));
        return we;
    }

    public static void sendSMS() {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        Map<String, String> headers = Collections.singletonMap("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", sms_phone_);
        querys.put("param", "**lcoal_area**:" + local_area_);
        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, Collections.emptyMap());
            int responseStatusCode = response.getStatusLine().getStatusCode();
            String responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");

            if (responseStatusCode == 200) {
                System.out.println("【Response】" + responseContent);
            } else {
                System.err.println("【Response】" + responseStatusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String APP_ID = "";
    private static final String API_KEY = "";
    private static final String SECRET_KEY = "";

    public static AipOcr getAipClient() {
        return getAipClient(API_KEY, SECRET_KEY);
    }

    public static AipOcr getAipClient(String apiKey, String secretKey) {
        AipOcr client = new AipOcr(APP_ID, apiKey, secretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }
    public static String result(AipOcr client,String url) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");//识别语言类型
        options.put("detect_direction", "true");//是否检测图像朝向
        options.put("detect_language", "true");//是否检测语言
        options.put("probability", "true");//是否输出段落信息
        options.put("url",url);//图片完整url
        JSONObject res = client.basicGeneralUrl("图片路径", options);
        return res.toString(2);
    }
    public static void main(String[] args) {
        System.out.println(result(getAipClient(),"https://checkimage.neea.cn/E9C5A0316F5353A71F6B8859E450F70A.jpg"));
    }


    public static void waitForSleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
