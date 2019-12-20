package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.dto.AccessTokenDto;
import com.blog.ljw.firstbolg.pojo.Account;
import com.blog.ljw.firstbolg.pojo.Category;
import com.blog.ljw.firstbolg.pojo.GithubUser;
import com.blog.ljw.firstbolg.pojo.User;
import com.blog.ljw.firstbolg.service.AccountService;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.UserService;
import com.blog.ljw.firstbolg.util.GithubLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private GithubLoginUtil githubLoginUtil;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @RequestMapping("/login")
    public String AdminLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                             HttpSession session, Model model){
        String str = "";
        Account account = accountService.getAccountBy(username,password);
        if(account !=null){
            Category category = articleService.getCategory();
            model.addAttribute("category",category);
            str = "redirect:/";
            session.setAttribute("account",account);
        }else{
            str = "login";
        }
        return str;
    }
    @RequestMapping("/callback")
    public String Authenrize(@RequestParam(name = "code") String code,
                             @RequestParam(name = "state") String state,
                             HttpSession session){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String accessToken = githubLoginUtil.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubLoginUtil.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            //System.out.println(githubUser.toString());
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            if(userService.getByAccountID(String.valueOf(githubUser.getId()))==null){
                userService.userAdd(user);
            }
            session.setAttribute("user",user);
            return "redirect:/";
        } else {
            //log.error("callback get github error,{}", githubUser);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}
