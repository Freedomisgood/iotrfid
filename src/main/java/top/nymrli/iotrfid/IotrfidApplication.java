package top.nymrli.iotrfid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.nymrli.iotrfid.mapper")
@SpringBootApplication
public class IotrfidApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotrfidApplication.class, args);
    }
}
