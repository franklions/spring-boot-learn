package junit4.learn.demo.mockitodemo;

import junit4.learn.demo.domain.ArticleCalculator;
import junit4.learn.demo.domain.ArticleDatabase;
import junit4.learn.demo.domain.ArticleManager;
import junit4.learn.demo.domain.UserProvider;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
public class MockActicleManagerTest extends SampleBaseTestCase {

    @Mock
    private ArticleCalculator calculator;
    @Mock
    private ArticleDatabase database;
    @Mock
    private UserProvider userProvider;

    private ArticleManager manager;

     @Before
     public void setup() {
            manager = new ArticleManager(userProvider, database, calculator);
       }
}
