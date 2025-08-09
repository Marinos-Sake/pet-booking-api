package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.dto.PaymentInsertDTO;
import com.petbooking.bookingapp.dto.PaymentReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentReadOnlyDTO> createPayment(
            @Valid @RequestBody PaymentInsertDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.addPayment(dto, user.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentReadOnlyDTO> getPaymentById(@PathVariable Long id) {
        PaymentReadOnlyDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PaymentReadOnlyDTO>> getAllPayments() {
        List<PaymentReadOnlyDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/my")
    public ResponseEntity<List<PaymentReadOnlyDTO>> getMyPayments(@AuthenticationPrincipal User user) {
        List<PaymentReadOnlyDTO> payments = paymentService.getMyAllPayments(user.getId());
        return ResponseEntity.ok(payments);
    }
}
