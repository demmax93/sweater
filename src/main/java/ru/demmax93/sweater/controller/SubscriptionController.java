package ru.demmax93.sweater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.demmax93.sweater.domain.User;
import ru.demmax93.sweater.domain.enums.TypeOfSubscription;
import ru.demmax93.sweater.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SubscriptionController {
    private final UserService userService;

    @GetMapping("/subscribe/{user}")
    public String subscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(Model model, @PathVariable User user, @PathVariable String type) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if (TypeOfSubscription.SUBSCRIPTIONS.equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        }
        if (TypeOfSubscription.SUBSCRIBERS.equals(type)) {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }
}
