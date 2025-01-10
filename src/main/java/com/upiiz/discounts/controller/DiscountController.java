package com.upiiz.discounts.controller;

import com.upiiz.discounts.entities.Discount;
import com.upiiz.discounts.responses.CustomResponse;
import com.upiiz.discounts.services.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://wcbdf-rca-api-discounts.onrender.com"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1/discounts")
@Tag(
        name = "Discounts"
)
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<List<Discount>>> getDiscounts() {
        List<Discount> expens = new ArrayList<>();
        Link allDiscountsLink = linkTo(DiscountController.class).withSelfRel();
        List<Link> links = List.of(allDiscountsLink);
        try {
            expens = discountService.getAllDiscounts();
            if (!expens.isEmpty()) {
                CustomResponse<List<Discount>> response = new CustomResponse<>(1, "Descuentos encontrados", expens, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Descuentos no encontrados", expens, links));
            }
        } catch (Exception e) {
            CustomResponse<List<Discount>> response = new CustomResponse<>(500, "Error interno de servidor", expens, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<CustomResponse<Discount>> getDiscountById(@PathVariable Long id) {
        Optional<Discount> discount = null;
        CustomResponse<Discount> response = null;
        Link allDiscountsLink = linkTo(DiscountController.class).withSelfRel();
        List<Link> links = List.of(allDiscountsLink);
        try {
            discount = discountService.getDiscountById(id);
            if (discount.isPresent()) {
                response = new CustomResponse<>(1, "Descuento encontrado", discount.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Descuento no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<CustomResponse<Discount>> crearDiscount(@RequestBody Discount discount) {
        Link allDiscountsLink = linkTo(DiscountController.class).withSelfRel();
        List<Link> links = List.of(allDiscountsLink);
        try {
            Discount discount1 = discountService.createDiscount(discount);
            if (discount1 != null) {
                CustomResponse<Discount> response = new CustomResponse<>(1, "Descuento creado", discount1, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Descuento no encontrado", discount1, links));
            }
        } catch (Exception e) {
            CustomResponse<Discount> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<CustomResponse<Discount>> updateDiscount(@RequestBody Discount discount, @PathVariable Long id) {
        Link allDiscountsLink = linkTo(DiscountController.class).withSelfRel();
        List<Link> links = List.of(allDiscountsLink);
        try {
            discount.setDiscountId(id);
            if (!discountService.getDiscountById(id).equals("")) {
                Discount discountEntity = discountService.updateDiscount(discount);
                CustomResponse<Discount> response = new CustomResponse<>(1, "Descuento actualizado", discountEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<Discount> response = new CustomResponse<>(0, "Descuento no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<Discount> response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<CustomResponse<Discount>> deleteDiscountById(@PathVariable Long id) {
        Optional<Discount> discountEntity = null;
        CustomResponse<Discount> response = null;
        Link allDiscountsLink = linkTo(DiscountController.class).withSelfRel();
        List<Link> links = List.of(allDiscountsLink);

        try {
            discountEntity = discountService.getDiscountById(id);
            if (discountEntity.isPresent()) {
                discountService.deleteDiscount(id);
                response = new CustomResponse<>(1, "Descuento eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Descuento no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}