package top.nymrli.iotrfid.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nymrli.iotrfid.service.UserService;
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
@RequestMapping("/iotrfid/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    public JsonResult findAll() {
        return JsonResult.ok(userService.list());
    }
}
