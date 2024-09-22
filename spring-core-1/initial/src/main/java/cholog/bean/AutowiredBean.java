package cholog.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowiredBean {
    /*
    어떤 방법으로 Component에 Bean을 주입하는지 학습하기
     */

    //Autowired를 이용하여 Container에 등록된 bean객체를 가져옴
    @Autowired
    private SpringBean springBean;

    public String sayHello() {
        return springBean.hello();
    }
}
