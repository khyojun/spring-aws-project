package org.springboot.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HelloResponseDtoTest {


    @Test
    public void 롬복_기능_테스트(){

        //given
        String name= "test";
        int amount=10000;

        //when
        HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);

        //then
        Assertions.assertThat(name).isEqualTo(helloResponseDto.getName());
        Assertions.assertThat(amount).isEqualTo(helloResponseDto.getAmount());

    }

}
