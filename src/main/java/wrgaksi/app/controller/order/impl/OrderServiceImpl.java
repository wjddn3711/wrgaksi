package wrgaksi.app.controller.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.order.OrderService;
import wrgaksi.app.model.order.OrderDAO;
import wrgaksi.app.model.order.OrderSet;

import java.util.ArrayList;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Override
    public void insert(OrderSet os) {
        orderDAO.insert(os);
    }

    @Override
    public ArrayList<OrderSet> selectAll() {
        return orderDAO.selectAll();
    }

    @Override
    public OrderSet selectSearch(OrderSet os) {
        return orderDAO.selectSearch(os);
    }

    @Override
    public void delete(OrderSet os) {
        orderDAO.delete(os);
    }
}
