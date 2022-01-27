package wrgaksi.app.controller.customer.impl;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.customer.CustomerService;
import wrgaksi.app.model.customer.CustomerDAO;
import wrgaksi.app.model.customer.CustomerVO;

import java.util.HashMap;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public CustomerVO validationId(CustomerVO vo) {
        try {
            return customerDAO.validationId(vo);
        } catch (Exception e) {
            return null; // 해당하는 아이디가 없다면 null 을 반환하도록
        }
    }

    @Override
    public CustomerVO validationPw(CustomerVO vo) {
        try {
            return customerDAO.validationPw(vo);
        } catch (Exception e) {
            return null; // 해당하는 아이디가 없다면 null 을 반환하도록
        }
    }


    @Override
    public void insert(CustomerVO vo) {
        customerDAO.insert(vo);
    }

    @Override
    public int id_check(CustomerVO vo) {
       return customerDAO.id_check(vo);
    }

    @Override
    public CustomerVO login_check(CustomerVO vo) {
        try {
            return customerDAO.login_check(vo);
        } catch (Exception e) {
            return null; // 만약 해당하는 아이디가 존재하지 않을시
        }
    }

    @Override
    public CustomerVO selectOne(CustomerVO vo) {
        return customerDAO.selectOne(vo);
    }

    @Override
    public void update(CustomerVO vo) {
        customerDAO.update(vo);
    }

    @Override
    public void delete(CustomerVO vo) {
        customerDAO.delete(vo);
    }


    // 서비스에 CoolSms 를 추가
    @Override
    public void updateTemp(CustomerVO vo) {
        System.out.println(vo);
        String api_key = "NCS5FYK77D9LAFN2";
        String api_secret = "7LK6XQNXLUMEUAAG46LBRIUDV7SP1IDE";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", vo.getPhone_number());	// 수신전화번호
        params.put("from", "01046275401");	// 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "회원님의 임시 비밀번호는 "+vo.getTemp_password()+" 입니다. 확인후 로그인 해주세요");
        params.put("app_version", "test app 1.2"); // application name and version
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println("문자전송 완료");
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        customerDAO.updateTemp(vo);
    }
}
