package com.yigiter.librarymanagement.service;

import com.yigiter.librarymanagement.domain.Borrower;
import com.yigiter.librarymanagement.dto.BorrowerDTO;
import com.yigiter.librarymanagement.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BorrowerRepository borrowerRepository;
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        Borrower borrowerEmail=getBorrowerByEmail(borrowerDTO.getEmail());
        Borrower borrowerPhone=getBorrowerByPhoneNumber(borrowerDTO.getPhoneNumber());
        if (borrowerEmail!=null ) {
            throw new RuntimeException("Borrower email already exist");
        } else if (borrowerPhone != null) {
            throw new RuntimeException("Borrower phone already exist");
        } else {
            String sql2 = "INSERT INTO tbl_borrowers (email, name, phone_number) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql2, borrowerDTO.getEmail(),borrowerDTO.getName(), borrowerDTO.getPhoneNumber());
        }
        return borrowerDTO;
    }
    public BorrowerDTO getBorrower(String email) {
        Borrower borrower =getBorrowerByEmail(email);
        if (borrower==null) {
            throw new RuntimeException("Not found Borrower");
        }
        return borrowerToBorrowerDTO(borrower);
    }

    public String removeBorrower(String email) {
        Borrower borrower =getBorrowerByEmail(email);
        if (borrower!=null) {
            jdbcTemplate.update("DELETE FROM tbl_borrowers WHERE email='"+email+"'");
            return "Borrower deleted successfully ";
        }else {
            throw new RuntimeException("Not found Borrower");
        }
    }
        //assist Methods
    public Borrower getBorrowerByEmail(String email) {
        Borrower borrower;
        String sql="SELECT * FROM tbl_borrowers WHERE email='"+email+"'";
        try {
            borrower = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Borrower.class));
            return borrower;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public Borrower getBorrowerByPhoneNumber(String phone){
        Borrower borrower;
        String sql="SELECT * FROM tbl_borrowers WHERE phone_number= '" +phone + "'";
        try {
            borrower = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Borrower.class));
            return borrower;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
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
