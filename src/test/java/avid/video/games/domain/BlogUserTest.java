package avid.video.games.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import avid.video.games.web.rest.TestUtil;

public class BlogUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogUser.class);
        BlogUser blogUser1 = new BlogUser();
        blogUser1.setId(1L);
        BlogUser blogUser2 = new BlogUser();
        blogUser2.setId(blogUser1.getId());
        assertThat(blogUser1).isEqualTo(blogUser2);
        blogUser2.setId(2L);
        assertThat(blogUser1).isNotEqualTo(blogUser2);
        blogUser1.setId(null);
        assertThat(blogUser1).isNotEqualTo(blogUser2);
    }
}
