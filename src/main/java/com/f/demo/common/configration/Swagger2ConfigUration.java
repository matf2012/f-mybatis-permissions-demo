package com.f.demo.common.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class Swagger2ConfigUration {

    @Bean
    public Docket accessToken() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")// 定义组
                .select() // 选择那些路径和 api 会生成 document
                .apis(RequestHandlerSelectors.basePackage("com.f.demo.module.cust.controller")) // 拦截的包路径
                .paths(PathSelectors.regex("/*/.*"))// 拦截的接口路径
                .build() // 创建
                .apiInfo(apiInfo()); // 配置说明
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据权限")// 标题
                .description("mybatis数据权限插件")// 描述
                .termsOfServiceUrl("https://www.lovejing.xyz")
                .contact(new Contact("风","https://www.lovejing.xyz","")).version("2.7.0").build();
    }
}
