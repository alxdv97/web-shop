package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
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

    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

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

    @PostMapping
    @Operation(
            summary = "Create order",
            description = "Create and return order"
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
}
