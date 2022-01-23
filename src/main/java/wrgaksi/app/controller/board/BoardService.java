package wrgaksi.app.controller.board;

import wrgaksi.app.model.board.BoardVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BoardService {
    public BoardVO selectOne(BoardVO data);
    public ArrayList<BoardVO> selectAll();
    public ArrayList<BoardVO> selectSearch(BoardVO vo);
    public ArrayList<BoardVO> selectFav();
    public ArrayList<BoardVO> selectMine(BoardVO vo);
    public void insert(BoardVO vo);
    public void delete(BoardVO vo);
    public void update(BoardVO vo);
}
