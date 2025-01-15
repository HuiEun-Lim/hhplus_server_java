package kr.hhplus.be.server.config.web;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        // 구현한 필터 등록
        filterFilterRegistrationBean.setFilter(new LogFilter());

        // 필터 순서 지정
        filterFilterRegistrationBean.setOrder(1);

        // 필터가 적용될 URL 지정
        filterFilterRegistrationBean.addUrlPatterns("/**");

        return filterFilterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }

}
