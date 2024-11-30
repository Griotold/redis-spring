package com.study.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
// Spring Boot 3.2 이상부터 사용 가능한 어노테이션
// Page 객체의 JSON 구조를 안정적으로 지원해준다.
// 없으면 Warning 로그 찍힌다.
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
