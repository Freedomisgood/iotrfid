package top.nymrli.iotrfid.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/iotrfid/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public boolean post(Device device) {
        return deviceService.save(device);
    }

    @GetMapping
    public JsonResult get(Long id) {
        return JsonResult.ok(deviceService.getById(id));
    }
}

