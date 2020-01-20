package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Add annotations here アノテーションの追加
 */
@Controller
@RequestMapping("/sample")
public class SampleController {
	
	//リクエストがGETの場合 GETはURLとしてわたす
	//POSTの場合はヘッダー情報として渡す
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		
		String sql = "SELECT id, name, email " 
				+ "FROM inquiry WHERE id = 1";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		model.addAttribute("title", "inquiry Form");
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));
		
		return "test";
		//returnでtest.htmlがレンダリングされる　htmlは自動で補完
	}
	
	
//	
//	
// 	private final JdbcTemplate jdbcTemplate;
//
// 	@Autowired
// 	public SampleController(JdbcTemplate jdbcTemplate) {
// 		this.jdbcTemplate = jdbcTemplate;
// 	}
// 	//インスタンス生成をフレームワークの機能に任せる→単体テストがしやすくなる？
//	
//	@GetMapping
//	public String test(Model model) {
//		
//		String sql
//
//　	return "test";
//	}

}
