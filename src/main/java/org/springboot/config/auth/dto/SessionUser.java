package org.springboot.config.auth.dto;

import java.io.Serializable;
import javax.mail.Session;
import lombok.Getter;
import org.springboot.domain.user.User;

@Getter
public class SessionUser implements Serializable  // 직렬화(Serialization)란 자바 시스템 내에서 사용하는 객체 또는 데이터를 자바시스템 외에서도 사용할 수 있도록 Byte 형태로 데이터를 변환하는 기술이다.(파일로 만든다고 보면된다.)
{

    private String name;
    private String email;
    private String picture;


    public SessionUser(User user){
        this.name=user.getName();
        this.email=user.getEmail();
        this.picture=user.getPicture();
    }


}
