package top.nymrli.iotrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Mrli
 * @since 2021-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Device对象", description = "设备")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 设备MAC地址
     */
    @ApiModelProperty(value = "MAC地址")
    private String macId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
