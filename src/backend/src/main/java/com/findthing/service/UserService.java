package com.findthing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.findthing.entity.User;
import com.findthing.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    
    public User findByOpenid(String openid) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid));
    }
    
    public User createUser(String openid, String nickname, String avatarUrl) {
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        userMapper.insert(user);
        return user;
    }
}