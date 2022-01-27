package wrgaksi.app.controller.order;

import wrgaksi.app.model.order.*;

import java.util.ArrayList;

public interface OrderService {
    public void insert(OrderSet os);
    public ArrayList<OrderSet> selectAll();
    public OrderSet selectSearch(OrderSet os);
    public void delete(OrderSet os);
}
