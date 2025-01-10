package com.upiiz.discounts.responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private int estado;
    private String msg;
    private T discounts;
    private List<Link> links;

}
