package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.PaymentInsertDTO;
import com.petbooking.bookingapp.dto.PaymentReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment", description = "Endpoints for managing payments and booking status updates")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Create a new payment",
            description = "Creates a new payment for the authenticated user and updates the related booking status accordingly."
    )
    @PostMapping
    public ResponseEntity<PaymentReadOnlyDTO> createPayment(
            @Valid @RequestBody PaymentInsertDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.addPayment(dto, user.getId()));
    }

    @Operation(
            summary = "[ADMIN] Get payment by ID",
            description = "Retrieves the details of a specific payment by its unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentReadOnlyDTO> getPaymentById(@PathVariable Long id) {
        PaymentReadOnlyDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(
            summary = "[ADMIN] Get all payments (admin)",
            description = "Retrieves a paginated list of all payments with optional filters."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<PaymentReadOnlyDTO>> getAllPayments(@ModelAttribute GenericFilters filters) {
        return ResponseEntity.ok(paymentService.getAllForAdmin(filters));
    }

    @Operation(
            summary = "Get my payments",
            description = "Retrieves all payments made by the currently authenticated user."
    )
    @GetMapping("/my")
    public ResponseEntity<List<PaymentReadOnlyDTO>> getMyPayments(@AuthenticationPrincipal User user) {
        List<PaymentReadOnlyDTO> payments = paymentService.getMyAllPayments(user.getId());
        return ResponseEntity.ok(payments);
    }
}
