package com.fjut.oj.controller;

import com.fjut.oj.interceptor.CheckUserPrivate;
import com.fjut.oj.pojo.JsonInfoVO;
import com.fjut.oj.pojo.TokenModel;
import com.fjut.oj.redis.TokenManager;
import com.fjut.oj.service.UserAuthService;
import com.fjut.oj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: axiang [2019/8/28]
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    UserService userService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    TokenManager tokenManager;

    /**
     * 登录认证，并使用redis做鉴权
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public JsonInfoVO doLogin(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password) {
        JsonInfoVO JsonInfoVO = new JsonInfoVO();
        Date currentDate = new Date();
        if ("".equals(username) || "".equals(password)) {
            JsonInfoVO.setFail("用户名或者密码为空！");
            return JsonInfoVO;
        }
        // 用户名不存在
        if (!userAuthService.queryUserExistByUsername(username)) {
            JsonInfoVO.setFail("登录失败！用户不存在！");
            return JsonInfoVO;
        }
        // 查询登录权限的解锁时间
        Date unlockTime = userAuthService.queryUserUnlockTimeByUsername(username);
        // 如果当前时间小于解锁时间，则表示账号还在锁定期，无法登录
        if (0 > currentDate.compareTo(unlockTime)) {
            JsonInfoVO.setFail("您的账号已暂时被锁定，请稍后登录。如有疑问，请联系管理员");
            return JsonInfoVO;
        }
        if (userAuthService.queryUserAuthByUsernameAndPassword(username, password)) {
            JsonInfoVO.setSuccess("登录成功！");
            TokenModel tokenModel = tokenManager.createToken(username);
            String auth = tokenManager.createAuth(tokenModel);
            JsonInfoVO.addInfo(username);
            JsonInfoVO.addInfo(auth);
        }
        else{
            Integer attemptCount =  userAuthService.queryUserAuthAttemptNumberByUsername(username);
            JsonInfoVO.setFail("登录失败！账号或密码不正确！您还有 "+ (5-attemptCount>=0?(5-attemptCount):0) +"次机会");
        }
        return JsonInfoVO;
    }

    @PostMapping("/logout")
    @CheckUserPrivate
    public JsonInfoVO logoutUser(@RequestParam("username") String username) {
        tokenManager.deleteToken(username);
        return new JsonInfoVO("SUCCESS", "下线成功");
    }
}
