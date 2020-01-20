package com.example.demo.app.inquiry;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InquiryForm {
	
	//htmlのインプットなどに対応した変数をまとめたクラス
	@NotNull
	@Size(min = 1, max = 20, message = "20文字以下で入力してください")
	private String name;
	
	@NotNull
	@Email(message = "Invalid E-mail Format")
	private String email;
	
	@NotNull
	private String contents;
	
	//コンストラクタ
	public InquiryForm() {
	}
	
	//以下ゲッター・セッター
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
