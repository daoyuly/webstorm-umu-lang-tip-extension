/**
 * Created by liudaoyu on 16/7/16.
 */

package com.umu.langtip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.regex.*;


public class LangFile {
    private static final String KEY_PATTERN = "\"?(.*?)\"?";
    private static String langData = "";

    private static boolean isEmpty(String value) {
        return (("".equals(value)) || (value == null) || "null".equals(value));
    }

    public String getWorkPath(String root) {
        String workPath = "";
        String loaderPath = "";
        workPath = root + File.separator;
        loaderPath = workPath + "json" + File.separator + "demo.json";
        return loaderPath;
    }


    public String find(String keyword) {
        String result = "";
        Pattern pattern = Pattern.compile("\"" + keyword + "\"(\\s)*:(\\s)*[^,}]*,?");
        Matcher matcher = pattern.matcher(langData);
        if (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }


    public String read(String fileName) {

        if (langData.length() > 0) {
            return langData;
        }
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        langData = sb.toString();
        return langData;

    }
}
