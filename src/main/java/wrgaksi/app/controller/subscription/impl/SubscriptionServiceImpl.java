package wrgaksi.app.controller.subscription.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.subscription.SubscriptionService;
import wrgaksi.app.model.subscription.Order_subscriptionDAO;
import wrgaksi.app.model.subscription.Order_subscriptionVO;
import wrgaksi.app.model.subscription.Product_setVO;

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    Order_subscriptionDAO order_subscriptionDAO;

    @Override
    public Product_setVO selectRandom(Order_subscriptionVO vo) {
        return order_subscriptionDAO.selectRandom(vo);
    }

    @Override
    public void insert(Order_subscriptionVO vo) {
        order_subscriptionDAO.insert(vo);
    }

    @Override
    public Order_subscriptionVO selectOne(Order_subscriptionVO vo) {
        try {
            return order_subscriptionDAO.selectOne(vo);
        } catch (Exception e) {
            return null; // 해당 정보가 없다면 null 반환
        }
    }

    @Override
    public Product_setVO selectProductSet(Order_subscriptionVO vo) {
        return order_subscriptionDAO.selectProductSet(vo);
    }

    @Override
    public boolean selectIsExist(Order_subscriptionVO vo){
        try {
            return order_subscriptionDAO.selectIsExist(vo);
        } catch (Exception e) {
            return false; // 예외가 발생했다면 해당 아이디의 주문정보가 없다는 것이기 때문에 false 반환
        }
    }
}
