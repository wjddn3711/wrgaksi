package wrgaksi.app.model.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("customerDAO")
public class CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String sql_insert = "insert into customer values(?,?,?,?,?,?)";
    private String sql_id_check = "select customer_id from customer where customer_id=?";
    private String sql_selectOne="select * from customer where customer_id=?";
    private String sql_update = "update customer set customer_password=?, customer_name=?, phone_number=?, ZIP_code=?, detailed_address=? where customer_id=?";
    private String sql_delete = "delete from customer where customer_id=? and customer_password=?";
    private String sql_login_check = "select * from customer where customer_id=? and customer_password";
    private String sql_selectValid_pw = "select * from customer where customer_id=? and phone_number=?";
    private String sql_selectValid_id = "select * from customer where customer_name=? and phone_number=?";
    private String sql_updateTemp = "update customer set customer_password=? where customer_id=?";

    public CustomerVO validationId(CustomerVO vo){ // 유효한 아이디가 있는지 찾기위한 DAO
        // 만약 id 가 존재한다면 id를 반환 아니라면 null
        System.out.println("[사용자]유효아이디 체크 수행중");
        String id = "";
        Object [] obj = {vo.getCustomer_name(),vo.getPhone_number()};
        return jdbcTemplate.queryForObject(sql_selectValid_id,obj, new CustomerRowMapper());
    }

    public CustomerVO validationPw(CustomerVO vo){ // 임시 비밀번호를 위한 DAO
        System.out.println("[사용자]유효비밀번호 체크 수행중");
        Object [] obj = {vo.getCustomer_id(),vo.getPhone_number()};
        return jdbcTemplate.queryForObject(sql_selectValid_pw,obj,new CustomerRowMapper());
    }

    public void updateTemp(CustomerVO vo){
        System.out.println("[사용자]임시비밀번호 업데이트 수행중");
        jdbcTemplate.update(sql_updateTemp,vo.getCustomer_password(),vo.getCustomer_id());
    }

    // 회원가입(c)
    public void insert(CustomerVO vo) {
        System.out.println("[사용자]회원가입 수행중");
        jdbcTemplate.update(sql_insert,vo.getCustomer_id(),vo.getCustomer_password(),vo.getCustomer_name(),vo.getPhone_number(),vo.getZIP_code(),vo.getDetailed_address());
    }

    // 아이디 중복 체크
    public int id_check(CustomerVO vo) {
        System.out.println("[사용자]아이디중복 체크 수행중");
        int check;
        Object[] obj = {vo.getCustomer_id()};
        CustomerVO result = jdbcTemplate.queryForObject(sql_id_check,obj, new CustomerRowMapper());
        if(result!=null){ // 이미 존재하거나 아이디가 공백일때
            check = 0;
        }
        else {
            check = 1;
        }
        return check;
    }

    // 로그인체크
    public CustomerVO login_check(CustomerVO vo) {
        System.out.println("[사용자]로그인 수행중");
        Object[] obj = {vo.getCustomer_id(),vo.getCustomer_password()};
        return jdbcTemplate.queryForObject(sql_login_check,obj,new CustomerRowMapper());
    }

    // 회원정보조회(r)
    public CustomerVO selectOne(CustomerVO vo) {
        System.out.println("[사용자]회원정보 조회 수행중");
        Object[] obj = {vo.getCustomer_id()};
        return jdbcTemplate.queryForObject(sql_selectOne,obj,new CustomerRowMapper());
    }


    // 정보수정(u)
    public void update(CustomerVO vo) {
        System.out.println("[사용자]정보수정 수행중");
        jdbcTemplate.update(sql_update,vo.getCustomer_password(),vo.getCustomer_name(),vo.getPhone_number(),vo.getZIP_code(),vo.getDetailed_address(),vo.getCustomer_id());
    }

    // 탈퇴(d)
    public void delete(CustomerVO vo) {
        System.out.println("[사용자]회원 탈퇴 수행중");
        jdbcTemplate.update(sql_delete,vo.getCustomer_id(),vo.getCustomer_password());
    }
}

class CustomerRowMapper implements RowMapper<CustomerVO>{
    @Override
    public CustomerVO mapRow(ResultSet rs, int i) throws SQLException {
        CustomerVO vo = new CustomerVO();
        vo.setCustomer_id(rs.getString("customer_id"));
        vo.setCustomer_password(rs.getString("customer_password"));
        vo.setCustomer_name(rs.getString("customer_name"));
        vo.setPhone_number(rs.getString("phone_number"));
        vo.setZIP_code(rs.getString("ZIP_code"));
        vo.setDetailed_address(rs.getString("detailed_address"));
        return vo;
    }
}