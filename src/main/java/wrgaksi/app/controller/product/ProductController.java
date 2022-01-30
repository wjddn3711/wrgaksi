package wrgaksi.app.controller.product;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import wrgaksi.app.controller.subscription.SubscriptionService;
import wrgaksi.app.model.product.ProductCart;
import wrgaksi.app.model.product.ProductSingleCart;
import wrgaksi.app.model.product.ProductVO;
import wrgaksi.app.model.subscription.Order_subscriptionVO;
import wrgaksi.app.model.subscription.Product_setVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@SessionAttributes({"cart","totalPrice","customer_id"})
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping("/subscription.pd")
    public String subscribe(Order_subscriptionVO order_subscriptionVO, Model model, HttpSession session){
        // 초기 구독 상품 가격설정
        int product_set_price = 190000;
        if(order_subscriptionVO.getSoup_check().equals("포함")){
            product_set_price = 220000;
        }
        order_subscriptionVO.setCustomer_id((String) model.getAttribute("customer_id"));
        // 랜덤으로 국 유무에 따라 밥상을 선택
        Product_setVO product_set = subscriptionService.selectRandom(order_subscriptionVO);
        // 세션에 저장
        session.setAttribute("product_set",product_set);
        session.setAttribute("product_set_price",product_set_price);
        model.addAttribute("msg","밥상 장바구니 추가 완료! 장바구니를 통해 확인해주세요!");
        model.addAttribute("url","subscription.jsp");
        return "redirect.jsp";
    }

    @RequestMapping("/productAdd.pd")
    public String addProduct(ProductSingleCart singleCart, ProductVO vo, Model model){
        System.out.println(model.getAttribute("customer_id"));
        if(model.getAttribute("customer_id")==null){
            return "login.jsp";
        }
        if(singleCart.getProduct_count()==0){
            // 만약 수량 정보가 없다면 1 로
            singleCart.setProduct_count(1);
        }// vo 에 product_number 세팅 후 dao 를 통하여 쿼리 수행
        singleCart.setProductVO(productService.selectOne(vo));
        System.out.println(singleCart);
        int totalPrice =0;

        ProductCart cart = null;
        // 만약 현재 장바구니에 아무것도 담겨져 있지 않다면? 새로만들기
        ArrayList<ProductSingleCart> singleProducts = null;
        boolean isContain = false;

        // 기존에 장바구니 정보가 있다면
        if(model.getAttribute("cart")!=null){
            cart = (ProductCart) model.getAttribute("cart");
            System.out.println(cart);
            singleProducts = cart.getSingleProducts();
            // 만약 장바구니에 해당 상품이 있을때는 수량 + 해줌
            for (ProductSingleCart singleProduct : singleProducts) {
                if(singleProduct.getProductVO().getProduct_number()==singleCart.getProduct_count()){
                    isContain = true;
                    singleProduct.setProduct_count(singleProduct.getProduct_count()+singleCart.getProduct_count()); // 기존에 있던 수량 + 받아온 수량
                    break;
                }
            }
        }
        else{
            cart = new ProductCart();
            System.out.println("새로운 장바구니 생성");
            singleProducts = new ArrayList<>();
        }
        if(!isContain){ // 새롭게 추가된 상품이라면
            singleProducts.add(singleCart); // 카트에 상품을 추가
            cart.setSingleProducts(singleProducts); // 다시 추가된 것을 업데이트
        }
        // 개별 상품에 대해 가격을 모두 더해줌
        for (ProductSingleCart singleProduct : singleProducts) {
            totalPrice+=singleProduct.getProduct_price();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart.jsp";
        // 장바구니 페이지로 이동
    }

    // 상품 리스트 반환
    @RequestMapping("/productList.pd")
    public String productList(Model model){
        model.addAttribute("productList",productService.selectAll());
        return "productList.jsp";
    }

    @RequestMapping("/productDetail.pd")
    public String productDetail(Model model, ProductVO vo){
        model.addAttribute("productVO",productService.selectOne(vo));
        return "productSingle.jsp";
    }

    @RequestMapping("/productFilter.pd")
    public String productCategory(ProductVO vo, Model model){
        String category = vo.getProduct_type();
        ArrayList<ProductVO> productList = new ArrayList<>();
        if(category.equals("메인")){
            productList= productService.selectMain();
        }
        else if(category.equals("반찬")){
            productList= productService.selectSide();
        }
        else if(category.equals("국")){
            productList= productService.selectSoup();
        }
        model.addAttribute("productList", productList);
        return "productList.jsp";
    }

    @RequestMapping("/productSearch.pd")
    public String productSearch(ProductVO vo, Model model){
        model.addAttribute("productList", productService.selectSearch(vo));
        return "productList.jsp";
    }

    @RequestMapping("/updateCart.pd")
    public String updateCart(Model model, ProductSingleCart singleCart, ProductVO vo){

        ProductCart cart = (ProductCart) model.getAttribute("cart"); // 세션에서 카트 정보를 갖고 온다
        ArrayList<ProductSingleCart> singleProducts = cart.getSingleProducts();
        System.out.println(vo.getProduct_number());
        int totalPrice = 0;
        for (ProductSingleCart singleProduct : singleProducts) {
            if(singleProduct.getProductVO().getProduct_number()==vo.getProduct_number()){
                singleProduct.setProduct_count(singleCart.getProduct_count());
            }
            totalPrice+=singleProduct.getProduct_price();
        }
        model.addAttribute("totalPrice",totalPrice);// 전체 금액을 세션에 저장
        cart.setSingleProducts(singleProducts); // 추가 한것으로 업데이트
        model.addAttribute("cart",cart);
        return "cart.jsp";
    }

    @RequestMapping("/deleteCart.pd")
    public String deleteCart(HttpServletRequest request, Model model, HttpSession session){
        // product_number 를 뷰에서 받아와 해당 상품을 지운뒤 다시 장바구니로
        if(request.getParameter("undoSubscribe")!=null){
            session.invalidate(); // 세션에는 구독정보만 저장되어있기에 invalidate 로 flush
//            model.addAttribute("product_set",null);
//            model.addAttribute("product_set_price", null);
            return "cart.jsp";
        }
        int product_number = Integer.parseInt(request.getParameter("product_number"));
        ProductCart cart = (ProductCart) model.getAttribute("cart");
        ArrayList<ProductSingleCart> singleProducts = cart.getSingleProducts();
        int totalPrice = (int) model.getAttribute("totalPrice");
        for (int i = 0; i < singleProducts.size(); i++) {
            if(singleProducts.get(i).getProductVO().getProduct_number()==product_number){
                totalPrice -= singleProducts.get(i).getProduct_price(); // 해당 상품 가격을 뺀다
                singleProducts.remove(i); // 해당 상품을 지우고 탈출
                break;
            }
        }
        cart.setSingleProducts(singleProducts); // 추가 한것으로 업데이트
        model.addAttribute("cart",cart);
        model.addAttribute("totalPrice",totalPrice);
        return "cart.jsp";
    }
}
