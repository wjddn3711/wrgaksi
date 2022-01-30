package wrgaksi.app.model.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository("productDAO")
public class ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    String sql_insert = "insert into product values(?,?,?,?,?)";
    String sql_delete = "delete from product";
    String sql_selectAll = "select * from product";
    String sql_selectOne = "select * from product where product_number=?";
    String sql_selectMain = "select * from product where product_type='메인'";
    String sql_selectSide = "select * from product where product_type='반찬'";
    String sql_selectSoup = "select * from product where product_type='국'";
    String sql_selectSearch = "select * from product where product_name like ? or details like ? or product_type like ?";

    public ArrayList<ProductVO> selectSearch(ProductVO vo) {
        Object[] obj = {"%"+vo.getSearchContent()+"%","%"+vo.getSearchContent()+"%","%"+vo.getSearchContent()+"%"};
        return (ArrayList<ProductVO>) jdbcTemplate.query(sql_selectSearch,obj,new ProductRowMapper());
    }

    public ArrayList<ProductVO> selectMain(){
        return (ArrayList<ProductVO>) jdbcTemplate.query(sql_selectMain, new ProductRowMapper());
    }

    public ArrayList<ProductVO> selectSide(){
        return (ArrayList<ProductVO>) jdbcTemplate.query(sql_selectSide, new ProductRowMapper());
    }

    public ArrayList<ProductVO> selectSoup(){
        return (ArrayList<ProductVO>) jdbcTemplate.query(sql_selectSoup, new ProductRowMapper());
    }

    public ArrayList<ProductVO> selectAll(){
        return (ArrayList<ProductVO>) jdbcTemplate.query(sql_selectAll, new ProductRowMapper());
    }

    // 추가 수정
    public ProductVO selectOne(ProductVO vo){
        Object[] obj = {vo.getProduct_number()};
        return jdbcTemplate.queryForObject(sql_selectOne,obj,new ProductRowMapper());
    }

    public void insertAll(ArrayList<ProductVO> datas) {
        for (ProductVO data : datas) {
            jdbcTemplate.update(sql_insert,data.getProduct_name(),
                    data.getProduct_number(),data.getProduct_price(),
                    data.getProduct_type(),data.getProduct_image(),data.getDetails());
        }
    }

    public void deleteAll(){
        jdbcTemplate.update(sql_delete);
    }
    
}

class ProductRowMapper implements RowMapper<ProductVO> {

    @Override
    public ProductVO mapRow(ResultSet rs, int i) throws SQLException {
        ProductVO vo = new ProductVO();
        vo.setProduct_number(rs.getInt("product_number"));
        vo.setProduct_name(rs.getString("product_name"));
        vo.setProduct_price(rs.getInt("product_price"));
        vo.setProduct_type(rs.getString("product_type"));
        vo.setDetails(rs.getString("details"));
        vo.setProduct_image(rs.getString("product_image"));
        return vo;
    }
}