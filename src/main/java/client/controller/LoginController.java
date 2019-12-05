package client.controller;

import client.model.User;
import client.service.SpringRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    public static String token;

    private SpringRestClient service = SpringRestClient.getInstance();

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect: /user";
        }
        System.out.println("LoginController");
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping("/enter")
    public String postLogin(@RequestParam("login") String login, @RequestParam("password") String password) {
        String encode = new BCryptPasswordEncoder(11).encode(password);
        System.out.println(login + " " + password);
        String authorization = service.setLoginPost(login, password);
        System.out.println("LoginController/String authorization - " + authorization);
        token = authorization;

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        if (authenticate != null) {
            return "redirect:/user";
        }

        return "login";
    }

    @GetMapping("/user")
    public String getIndexPage(Authentication authentication, ModelMap model) {
        User user = service.getUserByLogin(authentication.getName());
        String name = user.getName();
        String role = user.getRoles().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            model.addAttribute("isAdmin", true);
        }

        model.addAttribute("user", name);
        return "welcome";
    }

}
