package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	@Autowired
	public InquiryController (InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List <Inquiry> list = inquiryService.getAll();
		
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("Jennie");
//		inquiry.setEmail("sample4@example.com");
//		inquiry.setContents("Hello bitches");
//		
//		inquiryService.update(inquiry);
//		
//		try {
//			inquiryService.update(inquiry);
//		} catch (InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "お問い合わせ一覧");
		
		return "inquiry/index_boot";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, 
			Model model,
			@ModelAttribute("complete") String complete) {
		//フラッシュスコープの値をhtmlでレンダリングできるようにする
		model.addAttribute("title", "お問い合わせフォーム");
		return "inquiry/form_boot";
	}
	
	@PostMapping("/form")
	public String formGoBack (InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "お問い合わせフォーム");
		return "inquiry/form_boot";
	
		//戻るボタンでformにアクセスしたときはpost
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryform, 
			BindingResult result, Model model) {
		//inputの内容にエラーあり/なしで表示ページの遷移をわけている
		if(result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "/inquiry/form_boot";
		}
		model.addAttribute("title", "確認ページ");
		return "inquiry/confirm_boot";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result, 
			Model model,
			RedirectAttributes redirectAttributes) {
		//ここでもバリデーションを組む（引数の前のアノテーションでエラーのチェック）
		if(result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "inquiry/form_boot";
		}
		//DBへの保存操作
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents( inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
		redirectAttributes.addFlashAttribute("complete", "登録されました!");
		// registered が表示されるとセッションのデータが破棄される（フラッシュスコープ）
		return "redirect:/inquiry/form";
		//　htmlファイルではなくURLを指す
		//　クライアントに1度レスポンスを返し、クライアントから再びリクエストが飛んでくる仕組み
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
	
}
