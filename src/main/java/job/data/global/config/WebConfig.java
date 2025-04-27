package job.data.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prioneer.homework.config.session.LogCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    //이거는 나중에 리액트랑 연동할려고 넣었습니다. 무시해도 상관 없어요.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api/** 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // 리액트 앱의 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowCredentials(true); // 쿠키를 포함한 요청 허용
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/system/join","/system","/system/login" ,"/css/**", "/*.ico", "/error",
                        "/logout", "/*.jpg", "logo/*.png", "/*.gif", "/logo/**","/*.png",
                        "/js/**","/error");

    }
}