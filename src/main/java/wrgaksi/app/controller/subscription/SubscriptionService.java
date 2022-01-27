package wrgaksi.app.controller.subscription;

import wrgaksi.app.model.subscription.Order_subscriptionVO;
import wrgaksi.app.model.subscription.Product_setVO;

public interface SubscriptionService {
    public Product_setVO selectRandom(Order_subscriptionVO vo);
    public void insert(Order_subscriptionVO vo);
    public Order_subscriptionVO selectOne(Order_subscriptionVO vo);
    public Product_setVO selectProductSet(Order_subscriptionVO vo);
    public boolean selectIsExist(Order_subscriptionVO vo);
}
