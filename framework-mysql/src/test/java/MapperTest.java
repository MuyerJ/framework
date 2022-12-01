import com.muyer.MysqlAPP;
import com.muyer.time_test.Users;
import com.muyer.time_test.UsersMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Description: 
 * date: 2021/9/8 22:22
 * @author YeJiang
 * @version
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysqlAPP.class)
public class MapperTest{

    @Autowired
    private UsersMapper usersMapper;

    @Test
    public void contextLoads(){}

    @Test
    public void test() {
        for (int i = 0; i < 500000; i++) {
            long time = System.currentTimeMillis();
            usersMapper.saveUsers(Users.builder().timeDate(new Date(time)).timeLong(time).timeTimestamp(new Timestamp(time)).build());
        }
    }
}
