package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.dto.PaymentInsertDTO;
import com.petbooking.bookingapp.dto.PaymentReadOnlyDTO;
import com.petbooking.bookingapp.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentReadOnlyDTO> createPayment(@Valid @RequestBody PaymentInsertDTO dto) {
        PaymentReadOnlyDTO created = paymentService.addPayment(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentReadOnlyDTO> getPaymentById(@PathVariable Long id) {
        PaymentReadOnlyDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }
    @GetMapping
    public ResponseEntity<List<PaymentReadOnlyDTO>> getAllPayments() {
        List<PaymentReadOnlyDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/my")
    public ResponseEntity<List<PaymentReadOnlyDTO>> getMyPayments(@RequestParam("userId") Long userId) {
        List<PaymentReadOnlyDTO> payments = paymentService.getMyAllPayments(userId);
        return ResponseEntity.ok(payments);
    }
}
