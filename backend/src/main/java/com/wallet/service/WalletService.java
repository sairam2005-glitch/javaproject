package com.wallet.service;

import com.wallet.model.User;
import com.wallet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final UserRepository userRepository;

    public WalletService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user with a default balance of 1000.
     */
    public String register(String email, String password) {
        // Check if user already exists
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }

        User user = new User(email, password);
        userRepository.save(user);
        return "Registration successful! You have been given a starting balance of ₹1000.";
    }

    /**
     * Login with email and password.
     */
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));
    }

    /**
     * Get the wallet balance for a user.
     */
    public BigDecimal getBalance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return user.getBalance();
    }

    /**
     * Send money from one user to another.
     * Uses @Transactional to ensure atomicity.
     */
    @Transactional
    public String sendMoney(String fromEmail, String toEmail, BigDecimal amount) {
        // Validate: cannot send to self
        if (fromEmail.equalsIgnoreCase(toEmail)) {
            throw new RuntimeException("You cannot send money to yourself.");
        }

        // Validate: amount must be positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero.");
        }

        // Fetch sender
        User sender = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found."));

        // Fetch receiver
        User receiver = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new RuntimeException("Recipient not found."));

        // Validate: sender has enough balance
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance. You have ₹" + sender.getBalance());
        }

        // Transfer money
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(receiver);

        return "Successfully sent ₹" + amount + " to " + toEmail + ".";
    }
}
