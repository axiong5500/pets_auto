package com.example.demo.selenium;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.selenium.util.WebDriverUtils.*;

/**
 * @ClassName: PETS
 * @Description:
 * @Author lianyixiong
 * @Date 2023/7/4
 * @Version 1.0
 */
public class PETS {
    //登录url
    private static String login_url_ = "https://passport.neea.edu.cn/PETSLogin?ReturnUrl=https://pets-bm.neea.edu.cn/Home/VerifyPassport/?LoginType=0";
    private static String login_phone_ = "";
    private static String login_pwd_ = "";

    public static void main(String[] args) {
        //登录
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");
        WebDriver webDriver = new ChromeDriver(options);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript("Object.defineProperty(window.navigator, 'webdriver', { get: () => false });");

        webDriver.manage().window().maximize();
        webDriver.get(login_url_);
        Cookie cookie = new Cookie("Cookie", "6yLBm7kzH2xGO=5kjZXtUwA0UbFoK7JpI3_0Q3xRiXBWvOHqTge821KgnEdlnFtQZ88O6XjO5P80BWKvK_rPa1ldtnicNiy_FDWTG; 6yLBm7kzH2xGP=0WBb4QJ61fH7RId8aNLzFLh7CCEEo9ewRkGbPpV6r331CUuY8YRkvRNBTf0v8KcNoAkcNPuu9XSMOxxklQA4b6fUT.oEEYKi_ZeND7VAn.KlwmQIUcl0Dj.elzF_8SQJN4.xCYQdKT9UlZLkfv0KwojyfWCzII4XTWwXd9Xj.7iwKbJuU4jbd_oGbBT93RATqAuSTLg9h_SnpgMPdYSoLD5uH1EI4pADjktyKf33RoWeY6EdU3ISkN8Ff71O3jlpEGuu5pOCrgVoTFK8qLy2okr.w9pKoHqyjCFHWj2IyyhrWjWOGSI9y53NQHliYIf2cxqvf1dFBlvVTWr8l8keBbNNfa.V7CXZuT.APdOsCsg; _abfpc=88457dacbc53bb64239c1ddebfa53a132459fe06_2.0; cna=66454e0b6d3e3c485118b1eb77801fa3; ZXFFemgWA2YMO=5hYm5QbQAtZEWOQF9mIfz7arf0LwcydHgGRd6PSesZp_VMonJwH0RjP4NRdvQvlTkKgteEAWiaSLyWjPHIsmnoA; ******1=!KClB+qZ4jYD1LbuU4F8H9X/PEm3qNAzWH7ySMCgdfvbrnld46E6K48yXyXw9jfTHy5zn4eFSNgvm6kY=; ASP.NET_SessionId=2pr3pq035l3bvnu5w0ombt3i; __RequestVerificationToken=sGGo1SOmXfZwRMoS0PPuiGTyyTJc2v6MlUvZfPmb4z0Qq-ifRtsvtuw-cHCQ5DSMzSbaXe3D6eoSnaNZW9kDxUscxSksc-qsC9KrnBNsU-o1; ******=!5OyjThuSzHe2ZhdF6X4dP1ycOKDRhAD0XIbEtDFaY60UhsO7kekLNA2b1u3Em+F4T7LROhxa/fz4rA==; Hm_lvt_a4f7c7f81f672b97209121582bca25c7=1688432545; Hm_lvt_2e4ea7a3aed8b97b0a4e900eb4eaa7e6=1688432545; Hm_lpvt_a4f7c7f81f672b97209121582bca25c7=1688459704; Hm_lpvt_2e4ea7a3aed8b97b0a4e900eb4eaa7e6=1688459704; ZXFFemgWA2YMP=fFV_XIZ0PXSAb3psk_x.AWXZhqOajzsRs2BUGn_OifCbbdH8dr_1C3HRVu4um3G30wRDeARZbIJqbtVVM35r4qXNJc7zb8M3c.PHAOU9hIZlDIl_NmES4LGV6.14rOZLkazfDua_acZ_JWoiiopJTNvyFjQ3f4_axNZxkdWtyNeGPYjCaVLb14HDbc3KUDotpL49S4bodmZYdt9.hMSqVhuQfPKebHZvCV0GfPhD2gYU7k9ygcqNE.kOd9XZE92.n5iHDsp43Sj7_cfBB8umRfKhctmTHlldSc01ttnImMKgDDj5swuUjEmFoia53wxx3HT5zcDc8pgDRaONCUJI7lnUbl8xt4ASNoWJM19zNuU9mOItsajoeJFTLdKjgtJocgq2zrMzLixq5bHuST4basJ16lZzk9NOUayNFIslv69");
        webDriver.manage().addCookie(cookie);
        waitForSleep();
        webDriver.navigate().refresh();
        waitForSleep();
        sendKeys("输入账号",webDriver,"//*[@id=\"txtUserName\"]",new CharSequence[]{login_phone_});
        sendKeys("输入密码",webDriver,"//*[@id=\"txtPassword\"]",new CharSequence[]{login_pwd_});
        WebElement imageElement = findElement("获取验证码图片路径",webDriver,"//*[@id=\"imgCheckImage\"]");
        String imagePath = imageElement.getAttribute("src");
        String ocrResult = result(getAipClient(),imagePath);
        JSONObject jsonObj = JSONUtil.parseObj(ocrResult);
        String ocrWord = jsonObj.getJSONArray("words_result").getJSONObject(0).getStr("words");
        sendKeys("输入验证码",webDriver,"//*[@id=\"txtCheckImageValue\"]",new CharSequence[]{ocrWord});
        click("登录",webDriver,"//*[@id=\"ibtnLogin\"]");
        //操作
        //自动化检测
        //结束
    }
}
