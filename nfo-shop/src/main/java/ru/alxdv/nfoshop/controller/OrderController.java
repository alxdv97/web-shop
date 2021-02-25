package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/order")
@Tag(name = "Order controller", description = "Provides order API")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @GetMapping(value = "get-all")
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

    @GetMapping(value = "get/{id}")
    @Operation(
            summary = "Get order",
            description = "Return order by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrder(@Parameter(description = "Order's ID")
                                   @PathVariable Long id) {
        return mapper.toDTO(orderService.getOrder(id));
    }

    @PostMapping(value = "create")
    @Operation(
            summary = "Create order",
            description = "Create and return order"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@Parameter(description = "Order")
                                      @RequestBody OrderDTO orderDTO) {
        return mapper.toDTO(orderService.createOrder(mapper.toEntity(orderDTO)));
    }

    @PatchMapping("update")
    @Operation(
            summary = "Update order",
            description = "Update and return order"
    )
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@Parameter(description = "Order")
                                      @RequestBody OrderDTO orderDTO) {
        return mapper.toDTO(orderService.updateOrder(mapper.toEntity(orderDTO)));
    }

    @DeleteMapping("delete/{id}")
    @Operation(
            summary = "Delete order",
            description = "Delete order by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@Parameter(description = "Order's ID")
                               @PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
}
