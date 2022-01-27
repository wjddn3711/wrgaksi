package wrgaksi.app.model.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* [메서드명]					[반환타입]						[매개변수]						[기능]
 * selectAll() 				ArrayList<OrderSet>				x						전체 데이터 조회
 * selectSearch()  			OrderSet					int keyword				키워드(single_number)검색 단품&상세 주문 데이터 조회
 * insert() 				boolean						OrderSet os				단품&상세 주문 데이터 입력
 * delete() 				boolean						OrderSet os				단품&상세 주문 데이터 삭제
*/

@Repository("orderDAO")
public class OrderDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	String sql_insertS = "insert into order_single(customer_id, single_date) values (?,now())";
	String sql_insertD = "insert into order_detail(single_number,product_number,product_count) values((select max(single_number) from order_single),?,?)";
	String sql_selectAll = "select * from order_single order by single_number desc";
	String sql_selectSearch ="select * from order_single where single_number=?"; 
	String sql_delete = "delete from order_single where single_number=?"; //order_detail 테이블과 제약조건으로 묶여있기 때문에 상세주문도 같이 삭제
	String sql_selectAlldetail = "select * from order_detail where single_number=? order by detail_number desc";

	public void insert(OrderSet os) {
		System.out.println("[주문] insert 실행중");
		jdbcTemplate.update(sql_insertS,os.getSingle().getCustomer_id());
		for (int i = 0; i < os.getDetails().size(); i++) {
			jdbcTemplate.update(sql_insertD,os.getDetails().get(i).getProduct_number(),os.getDetails().get(i).getProduct_count());
		}
	}
	public ArrayList<OrderSet> selectAll() {
		System.out.println("[주문] selectAll 실행중");
		ArrayList<OrderSet> orders = new ArrayList<OrderSet>();
		// 주문의 정보를 조회
		List<Order_singleVO> osList = jdbcTemplate.query(sql_selectAll, new OrderSingleRowMapper());
		for (int i = 0; i < osList.size(); i++) {
			// 주문 세부 정보를 조회후 orderset에 저장
			OrderSet os = new OrderSet();
			Object[] obj = {osList.get(i).getSingle_number()};
			List<Order_detailVO> odList = jdbcTemplate.query(sql_selectAlldetail,obj, new OrderDetailRowMapper());
			os.setSingle(osList.get(i));
			os.setDetails((ArrayList<Order_detailVO>) odList);
			orders.add(os); // 모든 주문 정보들을 orders 에 저장
		}
		return orders;
	}
	public OrderSet selectSearch(OrderSet os) {
		System.out.println("[주문] selectSearch 실행중");
		OrderSet order = new OrderSet();
		Object[] objSingle = {os.getSingle().getSingle_number()};
		Order_singleVO oss = jdbcTemplate.queryForObject(sql_selectSearch,objSingle, new OrderSingleRowMapper());
		Object[] objDetail = {oss.getSingle_number()};
		List<Order_detailVO> odList = jdbcTemplate.query(sql_selectAlldetail,objDetail, new OrderDetailRowMapper());
		order.setSingle(oss);
		order.setDetails((ArrayList<Order_detailVO>) odList);
		return order;
	}
	public void delete(OrderSet os){
		System.out.println("[주문] delete 실행중");
		// 해당하는 주문내역 삭제
		jdbcTemplate.update(sql_delete,os.getSingle().getSingle_number());
	}
}

class OrderSingleRowMapper implements RowMapper<Order_singleVO>{
	@Override
	public Order_singleVO mapRow(ResultSet rs, int i) throws SQLException {
		Order_singleVO vo = new Order_singleVO();
		vo.setCustomer_id(rs.getString("customer_id"));
		vo.setSingle_date(rs.getString("single_date"));
		vo.setSingle_number(rs.getInt("single_number"));
		return vo;
	}
}

class OrderDetailRowMapper implements RowMapper<Order_detailVO> {

	@Override
	public Order_detailVO mapRow(ResultSet rs, int i) throws SQLException {
		Order_detailVO vo = new Order_detailVO();
		vo.setDetail_number(rs.getInt("detail_number"));
		vo.setProduct_number(rs.getInt("product_number"));
		vo.setProduct_count(rs.getInt("product_count"));
		vo.setSingle_number(rs.getInt("single_number"));
		return vo;
	}
}