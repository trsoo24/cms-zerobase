package com.zerobase.application;

import com.zerobase.client.service.customer.CustomerService;
import com.zerobase.client.service.seller.SellerService;
import com.zerobase.domain.SignInForm;
import com.zerobase.domain.model.Customer;
import com.zerobase.domain.model.Seller;
import com.zerobase.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.domain.common.UserType;
import org.zerobase.domain.config.JwtAuthenticationProvider;

import static com.zerobase.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        // 2. 토큰을 발행하고

        // 3. 토큰을 response 한다.
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }
}
