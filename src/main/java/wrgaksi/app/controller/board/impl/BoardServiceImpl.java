package wrgaksi.app.controller.board.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.board.BoardService;
import wrgaksi.app.model.board.BoardDAO;
import wrgaksi.app.model.board.BoardVO;

import java.util.ArrayList;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardDAO boardDAO;


    @Override
    public BoardVO selectOne(BoardVO data) {
         return boardDAO.selectOne(data);
    }

    @Override
    public ArrayList<BoardVO> selectAll() {
        return boardDAO.selectAll();
    }

    @Override
    public ArrayList<BoardVO> selectSearch(BoardVO vo) {
        return boardDAO.selectSearch(vo);
    }

    @Override
    public ArrayList<BoardVO> selectFav() {
        return boardDAO.selectFav();
    }

    @Override
    public ArrayList<BoardVO> selectMine(BoardVO vo) {
        try {
            return boardDAO.selectMine(vo);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void insert(BoardVO vo) {
        boardDAO.insert(vo);
    }

    @Override
    public void delete(BoardVO vo) {
        boardDAO.delete(vo);
    }

    @Override
    public void update(BoardVO vo) {
        boardDAO.update(vo);
    }
}
