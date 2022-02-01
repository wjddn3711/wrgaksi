package wrgaksi.app.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice("wrgaksi.app") // 해당 경로에서 예외가 발생하면 올수 있도록 설정
public class CommonExceptionHandler {

    // null pointer
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointer(Exception e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName("errors/defaultError.jsp");
        return mav;
    }


    // 404 에러
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex) {
        System.out.println("in 404");
        return "errors/error404.jsp";
    }
}
