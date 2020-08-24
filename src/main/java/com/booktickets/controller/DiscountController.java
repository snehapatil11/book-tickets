package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.Discount;
import com.booktickets.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DiscountController {

    @Autowired
    DiscountRepository discountRepository;

    @GetMapping("/discounts")
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @PostMapping("/discounts")
    public Discount createDiscount(@Valid @RequestBody Discount discount) {
        return discountRepository.save(discount);
    }

    @GetMapping("/discount/{id}")
    public Discount getDiscountById(@PathVariable(value = "id") Long discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", discountId));
    }

    @PutMapping("/discount/{id}")
    public Discount updateDiscount(@PathVariable(value = "id") Long discountId,
                             @Valid @RequestBody Discount details) {

        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", discountId));

        discount.setOfferName(details.getOfferName());
        discount.setPercent(details.getPercent());

        Discount updatedDiscount = discountRepository.save(discount);
        return updatedDiscount;
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable(value = "id") Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", discountId));

        discountRepository.delete(discount);

        return ResponseEntity.ok().build();
    }
}
