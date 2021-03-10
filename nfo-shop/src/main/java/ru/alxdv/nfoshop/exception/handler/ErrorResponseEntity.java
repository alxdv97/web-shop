package ru.alxdv.nfoshop.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class ErrorResponseEntity {
    private String error;
    private String cause;
}
