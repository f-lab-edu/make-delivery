package com.flab.makedel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    Jackson의 메세지 컨버터가 objectMapper를 사용하여 deserialize합니다.
    자바 객체로 deserialize하는 과정에서 자바 객체의 기본 생성자를 이용하기 때문에
    @NoArgsConstructor가 필요합니다.
    JsonCreator와 JsonProperty 어노테이션으로 프로퍼티를 명시하여
    이 문제를 해결할 수 있지만 Jackson에 의존적이 될 수 있다고 판단해
    @NoArgsConstructor를 이용하여 역직렬화할 때 문제를 해결했습니다.
 */

@Getter
@NoArgsConstructor
public class StoreCategoryDTO {

    private Long id;

    private String name;

}
