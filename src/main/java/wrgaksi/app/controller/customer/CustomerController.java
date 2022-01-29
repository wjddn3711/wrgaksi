package wrgaksi.app.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import wrgaksi.app.controller.subscription.SubscriptionService;
import wrgaksi.app.model.customer.CustomerVO;
import wrgaksi.app.model.subscription.Order_subscriptionVO;
import wrgaksi.app.model.subscription.Product_setVO;


@Controller
@SessionAttributes(value = "customer_id")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SubscriptionService subscriptionService; // 마이페이지에서 주문 정보를 받기위해

    @RequestMapping("/login.me")
    public String login(CustomerVO vo, Model model){
        System.out.println("login @cont");
        CustomerVO data = customerService.login_check(vo);
        if(data==null){
            model.addAttribute("msg","아이디나 비밀번호를 확인해주세요");
            model.addAttribute("url","login.jsp");
            return "redirect.jsp";
        }
        else{
            model.addAttribute("customer_id",data.getCustomer_id()); // id 값을 세션에 저장
            return "main.jsp";
        }
    }

    @RequestMapping("/logout.me")
    public String logout(SessionStatus sessionStatus, Model model){
        System.out.println("logout 수행중");
        System.out.println(model.getAttribute("customer_id"));
        model.addAttribute("customer_id", null);
        sessionStatus.setComplete();
        return "main.jsp";
    }

    @RequestMapping("/new.me")
    public String signUp(CustomerVO vo){
        customerService.insert(vo);
        return "login.jsp";
    }

    @RequestMapping(value = "/idCheck.me", method = RequestMethod.POST)
    @ResponseBody
    public String idCheck(CustomerVO vo){
//        System.out.println(vo);
        int idCheck = customerService.id_check(vo);
//        System.out.println(idCheck +"\n1: 존재하는 아이디, 0: 사용가능"); // 아이디 체크 로깅
        return ""+idCheck+"";
    }

    @RequestMapping("/updateUser.me")
    public String updateUser(CustomerVO vo, Model model){
        vo.setCustomer_id((String) model.getAttribute("customer_id"));
        System.out.println(vo);
        customerService.update(vo);
        return "mypage.me";
    }

    @RequestMapping("/deleteUser.me")
    public String deleteUser(CustomerVO vo, Model model){
        vo.setCustomer_id((String) model.getAttribute("customer_id"));
        customerService.delete(vo);
        return "main.do";
    }

    @RequestMapping(value = "findId.me", method = RequestMethod.POST, produces = "application/text; charset=UTF-8;")
    @ResponseBody
    public String findId(CustomerVO vo){
        CustomerVO customerVO = customerService.validationId(vo);
        if(customerVO==null){
            return "해당하는 아이디가 존재하지 않습니다";
        }
        else{
           String id = customerVO.getCustomer_id();
            int len = id.length()*3/4; // 아이디의 3/4만 보여줄수 있도록 나머지는 * 처리
            String fill = "";
            for (int i = len; i < id.length(); i++) {
                fill+='*';
            }
            return "회원님의 아이디는 "+id.substring(0,len)+fill+"입니다. \n확인 후 로그인 해주세요";
        }
    }

    @RequestMapping(value = "findPw.me", method = RequestMethod.POST, produces = "application/text; charset=UTF-8;")
    @ResponseBody
    public String findPw(CustomerVO vo){
        CustomerVO customerVO = customerService.validationPw(vo);
        if(customerVO==null){
            return "해당하는 정보가 존재하지 않습니다";
        }
        else{
            // 임시 비밀번호 만들기
            char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' , '*','!','@',
                    '#','$','%','^','&','*'}; // 임의로 숫자, 특수문자, 문자로 구성된 charSet 을 생성
            int idx = 0;
            String tempPassword = "";
            for (int i = 0; i < 8; i++) {  // 임시 비밀번호 8 자 생성
                // charSet 의 길이만큼의 랜덤한 숫자를 뽑아  랜덤 index 생성 후 해당 인덱스의 값을 결과에 더해준다
                idx = (int) (charSet.length * Math.random());
                tempPassword+=charSet[idx];
            }
            vo.setTemp_password(tempPassword); // 임시 비밀번호를 set
            customerService.updateTemp(vo);
            return "회원님의 임시 비밀번호가 설정되었습니다! \n로그인 후 마이페이지를 통해 변경 해주세요";
        }
    }

    @RequestMapping("/mypage.me")
    public String mypage(CustomerVO vo, Model model, Order_subscriptionVO ovo, Product_setVO productSet){
        String customer_id = (String) model.getAttribute("customer_id");
        vo.setCustomer_id(customer_id);
        System.out.println("mypage 수행중");
        model.addAttribute("userData", customerService.selectOne(vo)); // 유저 데이터를 받아 저장
        ovo.setCustomer_id(customer_id);
        Order_subscriptionVO order = subscriptionService.selectOne(ovo);
        System.out.println(order);
        if(order!=null){
            productSet = subscriptionService.selectProductSet(order);
            System.out.println(productSet);
            model.addAttribute("order", order);
            model.addAttribute("order_set",productSet);
        }
        return "mypage.jsp";
    }
}
