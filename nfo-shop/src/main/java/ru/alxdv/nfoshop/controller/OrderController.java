package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
@Tag(name = "Order controller", description = "Provides order API")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper mapper;

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Returns all orders"
    )
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get order",
            description = "Return order by ID"
    )
    public ResponseEntity<OrderDTO> getOrder(@Parameter(description = "Order's ID")
                                   @PathVariable Long id) {
        return new ResponseEntity<>(mapper.toDTO(orderService.getOrder(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/by-customer/{customerId}")
    @Operation(
            summary = "Get customer's orders",
            description = "Return all orders by customer ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getCustomerOrders(@Parameter(description = "Customer's ID")
                                            @PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("{customerId}")
    @Operation(
            summary = "Create customer's order",
            description = "Create and return order by customer ID"
    )
    public ResponseEntity<OrderDTO> createOrder(@Parameter(description = "Order")
                                      @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(mapper.toDTO(orderService.createOrder(mapper.toEntity(orderDTO))),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update order",
            description = "Update and return order"
    )
    public ResponseEntity<OrderDTO> updateOrder(@Parameter(description = "Order")
                                      @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(mapper.toDTO(orderService.updateOrder(mapper.toEntity(orderDTO))),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete order",
            description = "Delete order by ID"
    )
    public ResponseEntity deleteOrder(@Parameter(description = "Order's ID")
                               @PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PatchMapping(value = "add-product")
    @Operation(
            summary = "Add product to order",
            description = "Add product to order by its ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO addProduct(@Parameter(description = "Product's ID")
                               @RequestParam Long productId,
                               @Parameter(description = "Order's ID")
                               @RequestParam Long orderId) {
        return mapper.toDTO(orderService.addProductToOrder(orderId, productId));
    }
}
