package top.nymrli.iotrfid;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IotrfidApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test_add(){
        int a = 1, b = 2;
        assert a + b == 3;
    }

}
