package test_control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCreateEventController.class, TestJoinEventController.class, TestSearchEventController.class })
public class AllTests {

}
