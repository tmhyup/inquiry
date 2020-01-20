package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inquiry;

/*
 *  DaoではSQLの操作（データをひきだす）だけではなく
 *  Entityにデータを詰める役割を果たす
 */
@Repository
public class InquiryDaoImpl implements InquiryDao{
	
	private final JdbcTemplate jdbcTemplate;
	//DB操作用のクラス
	
	@Autowired
	public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertInquiry(Inquiry inquiry) {
		jdbcTemplate.update("INSERT INTO inquiry(name, email, contents, created) VALUES(?, ?, ?, ?)",
				inquiry.getName(), inquiry.getEmail(), inquiry.getContents(), inquiry.getCreated() );
			// 受け取り側で配列を受け取るので自由に個数を決められる→？の文だけ
			//	　SQLインジェクションを防ぐ効果
	}
	

	@Override
	public List<Inquiry> getAll() {
		
		String sql = "SELECT id, name, email, contents, created FROM inquiry";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		List<Inquiry> list = new ArrayList<Inquiry> ();
		// このリストにマップの中身を詰め直す
		
		for(Map<String, Object> result : resultList) {
			Inquiry inquiry = new Inquiry();
			//オブジェクトとして帰ってくる値を型変換する（キャスト）
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("email"));
			inquiry.setContents((String)result.get("contents"));
			//sqlではtimestamp型のものをjava上のtoLocalTimeになおす
			inquiry.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			//リストの中に中身（inquiry）をつめていく
			list.add(inquiry);
		}
		return list;
	}

	@Override
	public int updateInquiry(Inquiry inquiry) {
		return jdbcTemplate.update("UPDATE inquiry SET name = ?, email = ?, contents = ? WHERE id = ?",
				inquiry.getName(), inquiry.getEmail(), inquiry.getContents(), inquiry.getId());
	}
	
}
