package com.lixiaolong.javaadmin.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxl
 * @since 2022-10-05
 */
@Data
@ApiModel(value="SysUserRole对象", description="")
public class SysUserRole {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
