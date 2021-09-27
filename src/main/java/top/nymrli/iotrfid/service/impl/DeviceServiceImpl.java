package top.nymrli.iotrfid.service.impl;

import top.nymrli.iotrfid.entity.Device;
import top.nymrli.iotrfid.mapper.DeviceMapper;
import top.nymrli.iotrfid.service.DeviceService;
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
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

}
