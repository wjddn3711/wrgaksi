package wrgaksi.app.model.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository("boardDAO")
public class BoardDAO {

    /* [메서드명]					[반환타입]						[매개변수]					[기능]
     * selectAll() 				ArrayList<BoardVO>			x					전체 데이터 조회
     * selectSearch()  			ArrayList<BoardVO>			String keyword		키워드 검색 결과 조회
     * selectFav() 				ArrayList<BoardVO>			x					좋아요 순으로 결과 조회
     * selectMine()				ArrayList<BoardVO>			x					내가 작성한 글 조회
     * boolean insert() 		boolean						BoardVO vo			게시글 데이터 입력
     * boolean delete() 		boolean						BoardVO vo			게시글 데이터 삭제
     * boolean update() 		boolean						BoardVO vo			좋아요 +1 업데이트
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql_selectAll = "select * from board order by board_number desc";
    private String sql_selectSearch = "select * from board where board_title like ? or board_content like ? order by board_number desc";
    private String sql_selectFav = "select * from board order by board_fav desc";
    private String sql_selectMine = "select * from board where customer_id=? order by board_number desc";
    private String sql_selectOne = "select * from board where board_number=?";
    private String sql_insert = "insert into board(customer_id, board_date,board_title, board_content,board_fav) values (?,now(),?,?,0)";
    private String sql_delete = "delete from board where board_number=?";
    private String sql_update = "update board set board_fav = board_fav+1 where board_number=?";

    public BoardVO selectOne(BoardVO vo) {
        System.out.println("[게시판]selectOne 수행중");
        Object[] obj = {vo.getBoard_number()};
        return jdbcTemplate.queryForObject(sql_selectOne,obj, new BoardRowMapper());
    }

    public ArrayList<BoardVO> selectAll() {
        System.out.println("[게시판]selectAll 수행중");
        return (ArrayList<BoardVO>) jdbcTemplate.query(sql_selectAll, new BoardRowMapper());
    }

    public ArrayList<BoardVO> selectSearch(BoardVO vo) {
        System.out.println("[게시판]selectSearch 수행중");
        Object[] obj = {"%"+vo.getKeyword()+"%","%"+vo.getKeyword()+"%"};
        return (ArrayList<BoardVO>) jdbcTemplate.query(sql_selectSearch, obj, new BoardRowMapper());
    }

    public ArrayList<BoardVO> selectFav() {
        System.out.println("[게시판]selectFav 수행중");
        return (ArrayList<BoardVO>) jdbcTemplate.query(sql_selectFav, new BoardRowMapper());
    }

    public ArrayList<BoardVO> selectMine(BoardVO vo) throws Exception{
        System.out.println("[게시판]selectMine 수행중");
        Object[] obj = {vo.getCustomer_id()};
        return (ArrayList<BoardVO>) jdbcTemplate.query(sql_selectMine, obj,new BoardRowMapper());
    }

    public void insert(BoardVO vo) {
        System.out.println("[게시판]insert 수행중");
        jdbcTemplate.update(sql_insert,vo.getCustomer_id(),vo.getBoard_title(),vo.getBoard_content());
    }

    public void delete(BoardVO vo) {
        System.out.println("[게시판]delete 수행중");
        jdbcTemplate.update(sql_delete,vo.getBoard_number());
    }

    public void update(BoardVO vo) {
        System.out.println("[게시판]update 수행중");
        jdbcTemplate.update(sql_update,vo.getBoard_number());
    }
}

class BoardRowMapper implements RowMapper<BoardVO> {

    @Override
    public BoardVO mapRow(ResultSet rs, int i) throws SQLException {
        BoardVO vo = new BoardVO();
        vo.setBoard_number(rs.getInt("board_number"));
        vo.setCustomer_id(rs.getString("customer_id"));
        vo.setBoard_date(rs.getString("board_date"));
        vo.setBoard_title(rs.getString("board_title"));
        vo.setBoard_content(rs.getString("board_content"));
        vo.setBoard_fav(rs.getInt("board_fav"));
        return vo;
    }
}