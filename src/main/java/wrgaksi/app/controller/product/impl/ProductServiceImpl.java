package wrgaksi.app.controller.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrgaksi.app.controller.product.ProductService;
import wrgaksi.app.model.product.ProductDAO;
import wrgaksi.app.model.product.ProductVO;

import java.util.ArrayList;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDAO;

    @Override
    public ArrayList<ProductVO> selectSearch(ProductVO vo) {
        return productDAO.selectSearch(vo);
    }

    @Override
    public ArrayList<ProductVO> selectMain() {
        return productDAO.selectMain();
    }

    @Override
    public ArrayList<ProductVO> selectSide() {
        return productDAO.selectSide();
    }

    @Override
    public ArrayList<ProductVO> selectSoup() {
        return productDAO.selectSoup();
    }

    @Override
    public ArrayList<ProductVO> selectAll() {
        return productDAO.selectAll();
    }

    @Override
    public ProductVO selectOne(ProductVO vo) {
        return productDAO.selectOne(vo);
    }

    @Override
    public void insertAll(ArrayList<ProductVO> datas) {
        productDAO.insertAll(datas);
    }

    @Override
    public void deleteAll() {
        productDAO.deleteAll();
    }
}
