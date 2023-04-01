package com.zerobase.domain.customer;

import com.zerobase.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String email;
    private Integer balance;


    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getBalance()==null? 0 : customer.getBalance());
    }
}
