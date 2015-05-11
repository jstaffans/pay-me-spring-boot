package fi.bitrite.payme;

import com.darrinholst.sass_java.SassCompilingFilter;
import net.matlux.NreplServerSpring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PayMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayMeApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public NreplServerSpring repl() {
        return new NreplServerSpring(1112);
    }

    @Bean
    @Profile("dev")
    public FilterRegistrationBean sassFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        Map<String, String> initParams = new HashMap<>();
        initParams.put("configLocation", "sass/dev-config.rb");
        filterBean.setFilter(new SassCompilingFilter());
        filterBean.addUrlPatterns("*.css");
        filterBean.setInitParameters(initParams);
        return filterBean;
    }

}
