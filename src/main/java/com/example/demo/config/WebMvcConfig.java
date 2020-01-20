package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc この表記はspring boot2以降では不要 cssなどが読みこめなくなる
//This annotationn is not needed in spring boot2
//下このクラスが設定ファイルだということを認識させて自動的に読み込まれる

@Configuration      
public class WebMvcConfig implements WebMvcConfigurer{

}