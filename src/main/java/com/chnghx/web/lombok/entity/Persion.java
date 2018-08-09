package com.chnghx.web.lombok.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(includeFieldNames=true)
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor
@Builder
public class Persion {
	@NonNull
	private Integer id;
	private Integer height;
	private Integer age;
}
