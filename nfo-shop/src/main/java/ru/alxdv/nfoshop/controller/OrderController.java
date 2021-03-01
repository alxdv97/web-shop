package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/orders")
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
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    @Operation(
            summary = "Get order",
            description = "Return order by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrder(@Parameter(description = "Order's ID")
                             @PathVariable Long id) {
        return mapper.toDTO(orderService.getOrder(id));
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
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@Parameter(description = "Customer ID")
                                @PathVariable Long customerId) {
        return mapper.toDTO(orderService.createOrder(customerId));
    }

    @PutMapping
    @Operation(
            summary = "Update order",
            description = "Update and return order"
    )
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@Parameter(description = "Order")
                                @RequestBody OrderDTO orderDTO) {
        return mapper.toDTO(orderService.updateOrder(mapper.toEntity(orderDTO)));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete order",
            description = "Delete order by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@Parameter(description = "Order's ID")
                            @PathVariable Long id) {
        orderService.deleteOrderById(id);
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
