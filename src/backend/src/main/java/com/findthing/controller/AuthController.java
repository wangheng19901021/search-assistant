package com.findthing.controller;

import com.findthing.entity.User;
import com.findthing.service.UserService;
import com.findthing.utils.Result;
import com.findthing.utils.WeChatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final WeChatUtil weChatUtil;
    private final UserService userService;
    
    @PostMapping("/login")
    public Result<User> login(@RequestParam String code,
                             @RequestParam(required = false) String nickname,
                             @RequestParam(required = false) String avatarUrl) {
        String openid = weChatUtil.getOpenid(code);
        
        User user = userService.findByOpenid(openid);
        if (user == null) {
            user = userService.createUser(openid, nickname, avatarUrl);
        }
        
        return Result.success(user);
    }
}