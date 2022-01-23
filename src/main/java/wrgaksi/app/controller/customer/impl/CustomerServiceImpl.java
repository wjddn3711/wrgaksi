package wrgaksi.app.controller.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.customer.CustomerService;
import wrgaksi.app.model.customer.CustomerDAO;
import wrgaksi.app.model.customer.CustomerVO;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public CustomerVO validationId(CustomerVO vo) {
        return customerDAO.validationId(vo);
    }

    @Override
    public CustomerVO validationPw(CustomerVO vo) {
        return customerDAO.validationPw(vo);
    }

    @Override
    public void updateTemp(CustomerVO vo) {
        customerDAO.updateTemp(vo);
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
        return customerDAO.login_check(vo);
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
}
