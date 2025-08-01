package com.findthing.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeChatUtil {
    
    @Value("${wechat.app-id}")
    private String appId;
    
    @Value("${wechat.app-secret}")
    private String appSecret;
    
    private static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    
    public String getOpenid(String code) {
        try {
            String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    WECHAT_LOGIN_URL, appId, appSecret, code);
            
            String response = HttpUtil.get(url);
            JSONObject jsonObject = JSONUtil.parseObj(response);
            
            if (jsonObject.containsKey("errcode")) {
                log.error("WeChat login error: {}", response);
                throw new RuntimeException("WeChat login failed");
            }
            
            return jsonObject.getStr("openid");
        } catch (Exception e) {
            log.error("WeChat login error", e);
            throw new RuntimeException("WeChat login failed");
        }
    }
}