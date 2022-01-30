package wrgaksi.app.controller.product;

import wrgaksi.app.model.product.ProductVO;

import java.util.ArrayList;

public interface ProductService {
    public ArrayList<ProductVO> selectSearch(ProductVO vo);
    public ArrayList<ProductVO> selectMain();
    public ArrayList<ProductVO> selectSide();
    public ArrayList<ProductVO> selectSoup();
    public ArrayList<ProductVO> selectAll();
    public ProductVO selectOne(ProductVO vo);
    public void insertAll(ArrayList<ProductVO> datas);
    public void deleteAll();
}
