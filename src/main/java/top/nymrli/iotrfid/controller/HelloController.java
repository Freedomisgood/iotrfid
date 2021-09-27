package top.nymrli.iotrfid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: MrLi
 * @create: 2021-09-27 08:47
 **/
@RestController
public class HelloController {
    @GetMapping("/")
    public String get(){
        return "Say hi";
    }
}