package wrgaksi.app.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import wrgaksi.app.model.board.BoardVO;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    // 메인 페이지
    @RequestMapping("/main.do")
    public String mainPage(){
        System.out.println("메인페이지");
        return "main.jsp";
    }

    // 기본 게시판 리스트
    @RequestMapping("/boardList.do")
    public String boardList(BoardVO vo, Model model){
        System.out.println("로그 : board() @컨트롤러");
        model.addAttribute("boardList",boardService.selectAll());
        return "boardList.jsp"; // boardList 로 이동
    }

    // 게시판 카테고리 별
    @RequestMapping("/categoryBoard.do")
    public String boardCategory(@ModelAttribute("user") BoardVO vo, Model model){
        String category = vo.getCategory();
        if(category.equals("mine")){
            model.addAttribute("boardList", boardService.selectMine(vo));
        }
        else if(category.equals("recent")){
            return "boardList.do"; // default 가 recent 기 때문에 boardList.do 로 보낸다
        }
        else if(category.equals("favorite")){
            model.addAttribute("boardList", boardService.selectFav());
        }
        return "boardList.jsp";
    }

    // 게시판 좋아요
    @RequestMapping("/updateBoard.do")
    public String boardLikeUpdate(BoardVO vo){
        boardService.update(vo);
        return "boardList.do";
    }

    // 게시판 검색
    @RequestMapping("/searchBoard")
    public String boardSearch(BoardVO vo, Model model){
        // vo 에 자동으로 키워드 세팅
        model.addAttribute("boardList", boardService.selectSearch(vo));
        return "boardList.jsp";
    }

    // 게시판 등록
    @RequestMapping("/insertBoard")
    public String boardInsert(BoardVO vo){
        boardService.insert(vo);
        return "boardDone.jsp";
    }

    // 게시판 삭제
    @RequestMapping("/deleteBoard")
    public String boardDelete(BoardVO vo){
        boardService.delete(vo);
        return "boardList.do";
    }

    @RequestMapping("/boardDetail")
    public String boardDetail(BoardVO vo, Model model){
        model.addAttribute("boardDetail", boardService.selectOne(vo));
        return "boardOne.jsp";
    }

}

