package com.kss.controller;

import javax.validation.Valid;
import com.kss.dto.UserDTO;
import com.kss.dto.request.ForgotPasswordRequest;
import com.kss.dto.request.PasswordResetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kss.dto.request.LoginRequest;
import com.kss.dto.request.RegisterRequest;
import com.kss.dto.response.LoginResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.dto.response.KSSResponse;
import com.kss.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserJwtController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<KSSResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest)  {
        String token = userService.saveUser(registerRequest);
        KSSResponse response = new KSSResponse(ResponseMessage.REGISTER_RESPONSE_MESSAGE,true,token);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping(path = "confirm")
    public ResponseEntity<KSSResponse> confirm(@RequestParam("token") String token) {
        UserDTO userDTO = userService.confirmToken(token);
        KSSResponse response = new KSSResponse(ResponseMessage.ACCOUNT_CONFIRMED_RESPONSE,true,userDTO);
        return ResponseEntity.ok(response);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestHeader(value = "cartUUID",required = false)String cartUUID,@Valid @RequestBody LoginRequest loginRequest)  {
        System.out.println("cartUUID = " + cartUUID);
        LoginResponse response = userService.loginUser(cartUUID,loginRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<KSSResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        String message = userService.forgotPassword(forgotPasswordRequest);
        KSSResponse response = new KSSResponse(message,true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<KSSResponse> resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordResetRequest passwordResetRequest)  {
        String message = userService.confirmResetToken(token,passwordResetRequest);
        KSSResponse response = new KSSResponse(message,true);
        return ResponseEntity.ok(response);

    }

}
