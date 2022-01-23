package wrgaksi.app.controller.customer;


import wrgaksi.app.model.customer.CustomerVO;

public interface CustomerService {
    public CustomerVO validationId(CustomerVO vo);
    public CustomerVO validationPw(CustomerVO vo);
    public void updateTemp(CustomerVO vo);
    public void insert(CustomerVO vo);
    public int id_check(CustomerVO vo);
    public CustomerVO login_check(CustomerVO vo);
    public CustomerVO selectOne(CustomerVO vo) ;
    public void update(CustomerVO vo);
    public void delete(CustomerVO vo);
}
