package com.isg.referencedata.geography.country.I18N;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocalConfig {
    
    @Bean
    public LocaleResolver resolver(){

        AcceptHeaderLocaleResolver res = new AcceptHeaderLocaleResolver();
        res.setDefaultLocale(Locale.US);
        return res;
    }

    
}
