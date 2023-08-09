package intrum.assessment.contact.testcontext;

import intrum.assessment.contact.config.SpringContextConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringContextConfig.class)
public abstract class TestContextAPI {
}
