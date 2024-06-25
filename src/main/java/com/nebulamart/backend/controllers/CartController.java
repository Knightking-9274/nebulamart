package com.nebulamart.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nebulamart.backend.entities.Cart;
import com.nebulamart.backend.entities.User;
import com.nebulamart.backend.exceptions.ProductException;
import com.nebulamart.backend.exceptions.UserException;
import com.nebulamart.backend.requests.AddItemRequests;
import com.nebulamart.backend.responses.ApiResponse;
import com.nebulamart.backend.services.CartService;
import com.nebulamart.backend.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart Management",description="find user cart, add item to cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description="find cart by user id")
    public ResponseEntity<Cart>finUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
            User user = userService.UserProfileByJwt(jwt);
            Cart cart = cartService.findUserCart(user.getId());
            return new ResponseEntity<>(cart,HttpStatus.OK);
    }
    @PutMapping("/add")
    @Operation(description = "Item added to cart")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequests req,
                    @RequestHeader("Authorization")String jwt)throws UserException,ProductException{
                User user = userService.UserProfileByJwt(jwt);
                cartService.addCartItem(user.getId(), req);

                ApiResponse res = new ApiResponse();
                res.setMessage("Item added to cart");
                res.setStatus(true);
                return new ResponseEntity<>(res,HttpStatus.OK);
    }
    

}
