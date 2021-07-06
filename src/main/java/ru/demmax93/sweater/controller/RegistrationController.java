package ru.demmax93.sweater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.demmax93.sweater.domain.User;
import ru.demmax93.sweater.domain.dto.CaptchaResponseDto;
import ru.demmax93.sweater.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final UserService userService;
    private final RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name = "password2") String password2,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        boolean hasError = Boolean.FALSE;
        if (!StringUtils.hasLength(password2)) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
            hasError = Boolean.TRUE;
        }
        if (!Objects.equals(user.getPassword(), password2)) {
            model.addAttribute("passwordError", "Passwords are different");
            hasError = Boolean.TRUE;
        }
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (response != null && !response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
            hasError = Boolean.TRUE;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            hasError = Boolean.TRUE;
        }
        if (userService.checkUserExists(user)) {
            model.addAttribute("usernameError", "User exists!");
            hasError = Boolean.TRUE;
        }
        if (!hasError) {
            userService.addUser(user);
        }
        return hasError ? "/registration" : "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activatedUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
