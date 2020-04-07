import com.test.rpc.service.Calculate;
import com.test.rpc.util.ClassUtils;
import org.junit.Test;

/**
 * @author Yu
 * @since 2020/4/7
 */
public class ClassUtilsTest {

    @Test
    public void findInstantiate() {
        ClassUtils.findInstantiate(Calculate.class);
    }

}
