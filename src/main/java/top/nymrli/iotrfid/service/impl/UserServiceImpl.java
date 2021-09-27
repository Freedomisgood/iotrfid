package top.nymrli.iotrfid.service.impl;

import top.nymrli.iotrfid.entity.User;
import top.nymrli.iotrfid.mapper.UserMapper;
import top.nymrli.iotrfid.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mrli
 * @since 2021-09-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
