package com.yigiter.librarymanagement.repository;

import com.yigiter.librarymanagement.domain.Borrower;
import com.yigiter.librarymanagement.dto.BorrowerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BorrowersRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createBorrower(BorrowerDTO borrowerDTO){
        String sql2 = "INSERT INTO tbl_borrowers (email, name, phone_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql2, borrowerDTO.getEmail(),borrowerDTO.getName(), borrowerDTO.getPhoneNumber());
    }

    public void removeBorrower(String email){
        jdbcTemplate.update("DELETE FROM tbl_borrowers WHERE email='"+email+"'");
    }

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


}
