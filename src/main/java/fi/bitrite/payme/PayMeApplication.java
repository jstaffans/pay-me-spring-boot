package fi.bitrite.payme;

import com.darrinholst.sass_java.SassCompilingFilter;
import net.matlux.NreplServerSpring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PayMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayMeApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean sassFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        Map<String, String> initParams = new HashMap<>();
        initParams.put("configLocation", "sass/dev-config.rb");
        initParams.put("onlyRunWhenKey", "RUNTIME_ENVIRONMENT");
        initParams.put("onlyRunWhenValue", "development");
        SassCompilingFilter filter = new SassCompilingFilter();
        filterBean.setFilter(filter);
        filterBean.addUrlPatterns("*.css");
        filterBean.setInitParameters(initParams);
        return filterBean;
    }

    @Bean
    public NreplServerSpring repl() {
        return new NreplServerSpring(1112);
    }

}
