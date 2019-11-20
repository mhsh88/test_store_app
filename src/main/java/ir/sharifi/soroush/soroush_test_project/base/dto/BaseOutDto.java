package ir.sharifi.soroush.soroush_test_project.base.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
public @Data
class BaseOutDto<ID extends Serializable> implements Serializable {




    @Builder(builderMethodName = "BaseOutDtoBuilder")
    public BaseOutDto(ID id) {
        this.id = id;
    }

    private ID id;

}
