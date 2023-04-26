package org.example.controller;

import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-user")
    public void createUser() {
        userService.create();
    }

    @GetMapping("/add-money")
    public void addMoney(@RequestParam int userId, @RequestParam int value) {
        userService.addAmount(userId, value);
    }

    @GetMapping("/get-user")
    public ResponseEntity<String> getuser(@RequestParam int userId) {
        return ResponseEntity.ok(userService.read(userId).toString());
    }

    @GetMapping("/get-total-amount")
    public ResponseEntity<Integer> getTotalAmount(@RequestParam int userId) {
        return ResponseEntity.ok(userService.getTotalAmount(userId));
    }

    @GetMapping("/buy-stock")
    public void buyStock(@RequestParam int userId, @RequestParam String stockName) {
        userService.buyStock(userId, stockName);
    }

    @GetMapping("/sell-stock")
    public void sellStock(@RequestParam int userId, @RequestParam String stockName) {
        userService.sellStock(userId, stockName);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }
}
