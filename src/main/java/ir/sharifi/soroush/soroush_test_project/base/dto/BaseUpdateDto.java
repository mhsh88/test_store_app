package ir.sharifi.soroush.soroush_test_project.base.dto;

import lombok.Data;

import java.io.Serializable;

public @Data class BaseUpdateDto<ID extends Serializable> implements Serializable {

    private ID id;

}