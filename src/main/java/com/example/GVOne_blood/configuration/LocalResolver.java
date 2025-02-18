package com.example.GVOne_blood.configuration;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

//config for local resolver
//config language, ...
@Configuration
public class LocalResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    //config language, ...
    //override method resolveLocale
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language"); // lấy ra mã ngôn ngữ từ header của request
        return StringUtils.hasLength(language) ? new Locale(language) : Locale.getDefault();
        // nếu có thì trả về ngôn ngữ đó, không thì trả về ngôn ngữ mặc định (en)
    }
}
