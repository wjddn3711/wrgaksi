package wrgaksi.app.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import wrgaksi.app.model.customer.CustomerVO;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public String login(CustomerVO vo){
        customerService.login_check(vo);
    }
}
