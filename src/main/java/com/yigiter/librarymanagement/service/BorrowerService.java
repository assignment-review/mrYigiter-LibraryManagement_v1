package com.yigiter.librarymanagement.service;

import com.yigiter.librarymanagement.domain.Borrower;
import com.yigiter.librarymanagement.dto.BorrowerDTO;
import com.yigiter.librarymanagement.repository.BorrowerRepository;
import com.yigiter.librarymanagement.repository.BorrowersRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    private final BorrowersRepository borrowersRepository;
    private final BorrowerRepository borrowerRepository;
    public BorrowerService(BorrowersRepository borrowersRepository, BorrowerRepository borrowerRepository) {
        this.borrowersRepository = borrowersRepository;
        this.borrowerRepository = borrowerRepository;
    }

    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        Borrower borrowerEmail=borrowersRepository.getBorrowerByEmail(borrowerDTO.getEmail());
        Borrower borrowerPhone=borrowersRepository.getBorrowerByPhoneNumber(borrowerDTO.getPhoneNumber());
        if (borrowerEmail!=null ) {
            throw new RuntimeException("Borrower email already exist");
        } else if (borrowerPhone != null) {
            throw new RuntimeException("Borrower phone already exist");
        } else {
                borrowersRepository.createBorrower(borrowerDTO);
            }
        return borrowerDTO;
    }
    public BorrowerDTO getBorrower(String email) {
        Borrower borrower =borrowersRepository.getBorrowerByEmail(email);
        if (borrower==null) {
            throw new RuntimeException("Not found Borrower");
        }
        return borrowerToBorrowerDTO(borrower);
    }

    public void removeBorrower(String email) {
        Borrower borrower =borrowersRepository.getBorrowerByEmail(email);
        if (borrower!=null) {
                borrowersRepository.removeBorrower(email);
        }else {
            throw new RuntimeException("Not found Borrower");
        }
    }

    //convert methods
    private Borrower borrowerDTOToBorrower(BorrowerDTO borrowerDTO){
        Borrower borrower =new Borrower();
        borrower.setName(borrowerDTO.getName());
        borrower.setEmail(borrowerDTO.getEmail());
        borrower.setPhoneNumber(borrowerDTO.getPhoneNumber());
        return borrower;
    }

    private  BorrowerDTO borrowerToBorrowerDTO(Borrower borrower){
        BorrowerDTO borrowerDTO =new BorrowerDTO();
        borrowerDTO.setName(borrower.getName());
        borrowerDTO.setEmail(borrower.getEmail());
        borrowerDTO.setPhoneNumber(borrower.getPhoneNumber());
        return borrowerDTO;

    }






/*

// **** bu methodlar JpaRepository de kullnılan hazır crud sorguları repository package bulunan interfaceler aracılığla alan methodlar
    public BorrowerDTO bootcreateBorrower(BorrowerDTO borrowerDTO) {
            boolean email=borrowerRepository.existsByEmail(borrowerDTO.getEmail());
            boolean phone=borrowerRepository.existsByPhoneNumber(borrowerDTO.getPhoneNumber());
            if (email||phone){
                throw new RuntimeException("Borrower already exist");
            }
             Borrower borrower=borrowerDTOToBorrower(borrowerDTO);
            borrowerRepository.save(borrower);
        return borrowerDTO;
    }

    public BorrowerDTO bootgetBorrower(String email) {
        Borrower borrower =callBorrower(email);
        return borrowerToBorrowerDTO(borrower);
    }

    public String bootremoveBorrower(String email) {
        Borrower borrower=callBorrower(email);
        borrowerRepository.delete(borrower);
        return "Borrower deleted successfully ";
    }

    private Borrower callBorrower(String email){
       Borrower borrower = borrowerRepository.findByEmail(email).orElseThrow(
               ()->new RuntimeException("Not found Borrower")
       );
       return borrower;
    } */






}
