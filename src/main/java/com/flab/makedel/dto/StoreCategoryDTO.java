package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
/*
    Jackson의 메세지 컨버터가 objectMapper를 사용하여 deserialize합니다.
    자바 객체로 deserialize하는 과정에서 자바 객체의 기본 생성자를 이용하기 때문에
    @NoArgsConstructor가 필요합니다. 기본생성자로 객체를 만들고
    필드가 public이면 직접 assignment하고 private이라면 setter를 사용합니다.
    하지만 객체가 immutable하기를 원한다면 setter가 없어야한다.

    다른 방법으로는
    Jackson의 @JsonCreator를 이용하면 인자가 없는 기본생성자와 setter 없이도
    객체를 생성할 수 있으며 setter가 없는 불변객체를 생성할 수 있습니다.
    이 어노테이션을 생성자나 팩토리 메소드 위에 붙이면
    jackson이 해당 함수를 통해 객체를 생성하고 필드를 생성과 동시에 채웁니다.
    이렇게 생성자를 통해 필드 주입까지 끝내버리면 setter 함수가 필요 없게 됩니다.
    jackson을 통해 deserialze한 immutable한 객체를 얻을 수 있습니다.
 */

@Getter
public class StoreCategoryDTO {

    private final Long id;

    private final String name;

    @JsonCreator
    public StoreCategoryDTO(@JsonProperty(value = "id") Long id,
        @JsonProperty(value = "name") String name) {
        this.id = id;
        this.name = name;
    }

}
