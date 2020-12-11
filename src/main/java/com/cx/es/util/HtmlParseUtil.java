package com.cx.es.util;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author chenxin
 * @date 2020/12/11
 */
public final class HtmlParseUtil {

    public static void main(String[] args) throws IOException {
        List<Map<String, String>> mapList = HtmlParseUtil.parseJd("Java");
        System.out.println("mapList = " + mapList);
    }

    public static List<Map<String, String>> parseJd(String keywords) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();
        String url = "https://search.jd.com/Search?keyword=" + keywords;

        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");

        Elements elements = element.getElementsByTag("li");
        for (Element ele : elements) {
            Elements imgElement = ele.getElementsByTag("img");
            List<String> imgList = new ArrayList<>();
            for (Element imgEle : imgElement) {
                String src = imgEle.attr("src");
                imgList.add(src);
            }
            String priceText = ele.getElementsByClass("p-price").eq(0).text();
            String titleText = ele.getElementsByClass("p-name").eq(0).text();

            Map<String, String> map = new HashMap<>();
            map.put("imgList", JSON.toJSONString(imgList));
            map.put("priceText", JSON.toJSONString(priceText));
            map.put("titleText", JSON.toJSONString(titleText));
            result.add(map);
        }

        return result;
    }

}