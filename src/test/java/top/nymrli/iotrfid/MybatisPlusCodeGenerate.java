package top.nymrli.iotrfid;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

public class MybatisPlusCodeGenerate {
    /**
     * @author: Mrli
     * @description: MP代码生成器, 修改Step0中项目特有配置后即可使用(注:把生成的xml/XxxxMapper.xml手动放置到classpath下去)
     * @date: 2021/9/27 22:24
     */
    @Test
    public void autoGenerate() {
        // Step 0: 设置项目相关特有信息：
        // 填写代码生成的目录(需要修改)
        String projectPath = "F:\\SpringBootProject\\iot\\iotrfid";
        // 开发者信息
        String author = "Mrli";
        // 数据库 url 地址
        String dbName = "iot";
        String dbURL = "jdbc:mysql://localhost:3306/" + dbName +
                "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
        String dbUserName = "root";
        String dbPassword = "";
        // 设置生成路径的包名和模块名
        String parentPackageName = "top.nymrli";
        String moduleName = "iotrfid";
        // 需要对应生成的表名（可以同时操作多个表，使用` , `隔开）--> 目前没有测试成功
        String tableToCreate = "iotrfid_user";

        // Step1：代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // Step2：全局配置
        GlobalConfig gc = new GlobalConfig();
        // 拼接出代码最终输出的目录
        gc.setOutputDir(projectPath + "/src/main/java");
        // 配置开发者信息（可选）（需要修改）
        gc.setAuthor(author);
        // 配置是否打开目录，false 为不打开（可选）
        gc.setOpen(false);
        // 实体属性 Swagger2 注解，添加 Swagger 依赖，开启 Swagger2 模式（可选）
        gc.setSwagger2(true);
        // 重新生成文件时是否覆盖，false 表示不覆盖（可选）
        gc.setFileOverride(false);
        // 配置主键生成策略，此处为 ASSIGN_ID（可选）
        gc.setIdType(IdType.ASSIGN_ID);
        // 配置日期类型，此处为 ONLY_DATE（可选）
        gc.setDateType(DateType.ONLY_DATE);
        // 默认生成的 service 会有 I 前缀
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // Step3：数据源配置（需要修改）
        DataSourceConfig dsc = new DataSourceConfig();
        // 配置数据库 url 地址
        dsc.setUrl(dbURL);
//         dsc.setSchemaName("device"); // 可以直接在 url 中指定数据库名
        // 配置数据库驱动
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        // 配置数据库连接用户名
        dsc.setUsername(dbUserName);
        // 配置数据库连接密码
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        // Step:4：包配置
        PackageConfig pc = new PackageConfig();
        // 配置父包名
        pc.setParent(parentPackageName);
        // 配置模块名
        pc.setModuleName(moduleName);
        // 配置 entity 包名
        pc.setEntity("entity");
        // 配置 mapper 包名
        pc.setMapper("mapper");
        // 配置 service 包名
        pc.setService("service");
        // 配置 controller 包名
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // Step5：策略配置（数据库表配置）
        StrategyConfig strategy = new StrategyConfig();
        // ★指定表名（可以同时操作多个表，使用 , 隔开）
        strategy.setInclude(tableToCreate);
        // 配置数据表与实体类名之间映射的策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 配置数据表的字段与实体类的属性名之间映射的策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 配置 lombok 模式
        strategy.setEntityLombokModel(true);
        // 配置 rest 风格的控制器（@RestController）
        strategy.setRestControllerStyle(true);
        // 配置驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 配置表前缀，生成实体时去除表前缀
        // 此处的表名为 test_mybatis_plus_user，模块名为 test_mybatis_plus，去除前缀后剩下为 user。
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // Step6：执行代码生成操作
        mpg.execute();
    }
}
