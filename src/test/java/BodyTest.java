import net.dengzixu.body.AuthBody;
import net.dengzixu.enums.Message;
import net.dengzixu.message.body.SimpleMessageBody;
import net.dengzixu.message.content.NullContent;
import net.dengzixu.message.metadata.UserMetadata;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BodyTest {
    @Test
    void testAuthBody() {
        AuthBody authBody = new AuthBody(3080147, "");

        System.out.println(authBody.toJsonString());

        System.out.println(Arrays.toString(authBody.toJsonBytes()));
    }

    @Test
    void testApexMessageBody() {

        UserMetadata userMetadata = new UserMetadata(4283693, "too_long_name");


        SimpleMessageBody<NullContent> simpleMessageBody = new SimpleMessageBody<>(userMetadata,
                null,
                null,
                null,
                Message._NULL,
                new NullContent());

        System.out.println(simpleMessageBody.convertToString());
    }
}
