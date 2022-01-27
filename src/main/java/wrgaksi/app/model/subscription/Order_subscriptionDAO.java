package wrgaksi.app.model.subscription;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/* [메서드명]					[반환타입]							[매개변수]						[기능]
 * insert() 				boolean						Order_subscriptionVO		구독정보 Order_subscription 테이블에 입력
 * selectOne()  			Order_subscriptionVO		Order_subscriptionVO		customer_id를 통해 전체 Order_subscriptionVO 데이터 검색 
 * selectProductSet() 		Product_setVO				Order_subscriptionVO		customer_id에 해당하는 밥상 번호를 통해 밥상정보 검
*/

@Repository("order_subscriptionDAO")
public class Order_subscriptionDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

//	String sql_insert = "insert into order_subscription values ((select nvl(max(SUBSCRIPTION_NUMBER),0)+1 from ORDER_SUBSCRIPTION os),?,?,(select sysdate from dual),?,(select sysdate from dual),(select SYSDATE + (INTERVAL '30' DAY) from dual),?)";
	String sql_insert = "insert into order_subscription(customer_id, product_set_number, subscription_date, subscription_price, starting_date, expiration_date, soup_check) values (?,?,now(),?,(select date_add(now(),interval 1 day)),(select date_add(now(),interval 31 day)),?)";
	String sql_selectOne = "select * from order_subscription where customer_id=? order by subscription_number desc limit 1;";
	String sql_selectProductSet = "select * from product_set where product_set_number=?";
	String sql_selectIsExist = "select count(*) from order_subscription where customer_id=? and now() between ? and ?";
	String sql_selectProductSetNumber = "select product_set_number from product_set where soup_check=? ORDER BY rand() LIMIT 1";

	public Product_setVO selectRandom(Order_subscriptionVO vo) {
		Object[] objSoupCheck = {vo.getSoup_check()};
		// 국 포함, 미포함 여부에 따라 상품번호를 랜덤으로 추출
		int productNumber = jdbcTemplate.queryForObject(sql_selectProductSetNumber,objSoupCheck,Integer.class);
		Object[] objProductNum = {productNumber};
		return jdbcTemplate.queryForObject(sql_selectProductSet,objProductNum,new ProductSetRowMapper());
	}

	public void insert(Order_subscriptionVO vo) {
		int price = 190000;
		if(vo.getSoup_check().equals("포함")){
			price = 220000; // 만약 국이 포함되었다면 22만원으로 책정
		}
		jdbcTemplate.update(sql_insert,vo.getCustomer_id(),vo.getProduct_set_number(),price,vo.getSoup_check());
	}

	public Order_subscriptionVO selectOne(Order_subscriptionVO vo) throws Exception{
		Object[] obj = {vo.getCustomer_id()};
		return jdbcTemplate.queryForObject(sql_selectOne,obj, new OrderSubscriptionRowMapper());
	}

	public Product_setVO selectProductSet(Order_subscriptionVO vo) {
		Object[] obj = {vo.getProduct_set_number()};
		return jdbcTemplate.queryForObject(sql_selectProductSet,obj,new ProductSetRowMapper());
	}


	// 구독 주문시 이미 구독 주문 테이블의 기간내에 다시 신청할 경우 신청 불가하도록
	public boolean selectIsExist(Order_subscriptionVO vo) throws Exception{
		// 주문 날짜를 기준으로 만약 해당 기간내에 상품이 존재한다면 ? 주문이 불가하다 한뒤 세션에서 구독정보 없애준다
		Object[] objSelectOne = {vo.getCustomer_id()};
		Order_subscriptionVO os = jdbcTemplate.queryForObject(sql_selectOne,objSelectOne, new OrderSubscriptionRowMapper());
		// 만약 os 를 처리하는중 문제가 있다면 예외 발생
		Object[] objSelectExist = {vo.getCustomer_id(),os.getSubscription_date(),os.getExpiration_date()};
		int existCnt = jdbcTemplate.queryForObject(sql_selectIsExist,objSelectExist, Integer.class);
		if(existCnt >=1){ // 하나 이상의 중복되는 주문정보가 존재한다면
			return true;
		}
		return false;
	}
}

class ProductSetRowMapper implements RowMapper<Product_setVO>{
	@Override
	public Product_setVO mapRow(ResultSet rs, int i) throws SQLException {
		Product_setVO data = new Product_setVO();
		data.setProduct_set_number(rs.getInt("product_set_number"));
		data.setProduct_set_1st(rs.getString("product_set_1st"));
		data.setProduct_set_2nd(rs.getString("product_set_2nd"));
		data.setProduct_set_3rd(rs.getString("product_set_3rd"));
		data.setProduct_set_4th(rs.getString("product_set_4th"));
		data.setSoup_check(rs.getString("soup_check"));
		return data;
	}
}

class OrderSubscriptionRowMapper implements RowMapper<Order_subscriptionVO>{
	@Override
	public Order_subscriptionVO mapRow(ResultSet rs, int i) throws SQLException {
		Order_subscriptionVO data = new Order_subscriptionVO();
		data.setSubscription_number(rs.getInt("subscription_number"));
		data.setProduct_set_number(rs.getInt("product_set_number"));
		data.setCustomer_id(rs.getString("customer_id"));
		data.setSubscription_date(rs.getString("subscription_date"));
		data.setSubscription_price(rs.getInt("subscription_price"));
		data.setStarting_date(rs.getString("starting_date"));
		data.setExpiration_date(rs.getString("expiration_date"));
		data.setSoup_check(rs.getString("soup_check"));
		return data;
	}
}