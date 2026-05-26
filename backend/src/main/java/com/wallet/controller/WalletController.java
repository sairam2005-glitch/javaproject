package com.wallet.controller;

import com.wallet.model.User;
import com.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * POST /api/register
     * Body: { "email": "...", "password": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String message = walletService.register(email, password);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/login
     * Body: { "email": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            User user = walletService.login(email, password);
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "email", user.getEmail()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/balance?email=...
     */
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@RequestParam String email) {
        try {
            BigDecimal balance = walletService.getBalance(email);
            return ResponseEntity.ok(Map.of(
                    "email", email,
                    "balance", balance
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/send
     * Body: { "fromEmail": "...", "toEmail": "...", "amount": 100 }
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMoney(@RequestBody Map<String, Object> request) {
        try {
            String fromEmail = (String) request.get("fromEmail");
            String toEmail = (String) request.get("toEmail");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String message = walletService.sendMoney(fromEmail, toEmail, amount);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
