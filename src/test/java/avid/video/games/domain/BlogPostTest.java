package avid.video.games.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import avid.video.games.web.rest.TestUtil;

public class BlogPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogPost.class);
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setId(1L);
        BlogPost blogPost2 = new BlogPost();
        blogPost2.setId(blogPost1.getId());
        assertThat(blogPost1).isEqualTo(blogPost2);
        blogPost2.setId(2L);
        assertThat(blogPost1).isNotEqualTo(blogPost2);
        blogPost1.setId(null);
        assertThat(blogPost1).isNotEqualTo(blogPost2);
    }
}
