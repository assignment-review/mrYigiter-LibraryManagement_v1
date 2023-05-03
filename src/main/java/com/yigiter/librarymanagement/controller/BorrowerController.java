package com.yigiter.librarymanagement.controller;

import com.yigiter.librarymanagement.dto.BorrowerDTO;
import com.yigiter.librarymanagement.service.BorrowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {
    private final BorrowerService borrowerService;
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping("/add")
    public ResponseEntity<BorrowerDTO> addBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO){
        BorrowerDTO borrowerDTO1 =borrowerService.createBorrower(borrowerDTO);
        return new ResponseEntity<>(borrowerDTO1, HttpStatus.CREATED);
    }
    @GetMapping("/{email}")
    public ResponseEntity<BorrowerDTO> getBorrower(@PathVariable String email){
        BorrowerDTO borrowerDTO =borrowerService.getBorrower(email);
        return ResponseEntity.ok(borrowerDTO);
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<String> removeBorrower(@PathVariable String email){
        borrowerService.removeBorrower(email);
        String message ="Borrower deleted successfully ";
        return ResponseEntity.ok(message);
    }








    /*  // ****** bu endpointlerde Hibernate kullnılarak JPARepository ile yapılan sorgular için kullnılan methodlar ******

    @PostMapping("/boot/add")
    public ResponseEntity<BorrowerDTO> addBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO){
        BorrowerDTO borrowerDTO1 =borrowerService.createBorrower(borrowerDTO);
        return new ResponseEntity<>(borrowerDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/boot/{email}")
    public ResponseEntity<BorrowerDTO> getBorrower(@PathVariable String email){
        BorrowerDTO borrowerDTO =borrowerService.getBorrower(email);
        return ResponseEntity.ok(borrowerDTO);
    }

    @DeleteMapping("/boot/{email}")
    public ResponseEntity<String> removeBorrower(@PathVariable String email){
        String message =borrowerService.removeBorrower(email);
        return ResponseEntity.ok(message);
    }*/

}
