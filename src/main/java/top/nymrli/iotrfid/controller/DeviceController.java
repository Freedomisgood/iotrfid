package top.nymrli.iotrfid.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.nymrli.iotrfid.entity.Device;
import top.nymrli.iotrfid.service.DeviceService;
import top.nymrli.iotrfid.utils.JsonResult;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mrli
 * @since 2021-09-27
 */
@RestController
@RequestMapping("/iotrfid/devices")
@Api(tags = "设备管理")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    @ApiOperation(value = "新增设备", notes = "新增设备")
    public boolean post(Device device) {
        return deviceService.save(device);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询设备", notes = "新增设备")
    public JsonResult get(@PathVariable Long id) {
        return JsonResult.ok(deviceService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "查询设备列表", notes = "查询设备列表")
    public JsonResult findAll() {
        return JsonResult.ok(deviceService.list());
    }

}

