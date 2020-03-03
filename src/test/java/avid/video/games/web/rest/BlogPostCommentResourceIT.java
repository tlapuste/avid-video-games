package avid.video.games.web.rest;

import avid.video.games.AvidApp;
import avid.video.games.domain.BlogPostComment;
import avid.video.games.repository.BlogPostCommentRepository;
import avid.video.games.service.BlogPostCommentService;
import avid.video.games.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static avid.video.games.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BlogPostCommentResource} REST controller.
 */
@SpringBootTest(classes = AvidApp.class)
public class BlogPostCommentResourceIT {

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private BlogPostCommentRepository blogPostCommentRepository;

    @Autowired
    private BlogPostCommentService blogPostCommentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBlogPostCommentMockMvc;

    private BlogPostComment blogPostComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogPostCommentResource blogPostCommentResource = new BlogPostCommentResource(blogPostCommentService);
        this.restBlogPostCommentMockMvc = MockMvcBuilders.standaloneSetup(blogPostCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPostComment createEntity(EntityManager em) {
        BlogPostComment blogPostComment = new BlogPostComment()
            .body(DEFAULT_BODY);
        return blogPostComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPostComment createUpdatedEntity(EntityManager em) {
        BlogPostComment blogPostComment = new BlogPostComment()
            .body(UPDATED_BODY);
        return blogPostComment;
    }

    @BeforeEach
    public void initTest() {
        blogPostComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogPostComment() throws Exception {
        int databaseSizeBeforeCreate = blogPostCommentRepository.findAll().size();

        // Create the BlogPostComment
        restBlogPostCommentMockMvc.perform(post("/api/blog-post-comments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPostComment)))
            .andExpect(status().isCreated());

        // Validate the BlogPostComment in the database
        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAll();
        assertThat(blogPostCommentList).hasSize(databaseSizeBeforeCreate + 1);
        BlogPostComment testBlogPostComment = blogPostCommentList.get(blogPostCommentList.size() - 1);
        assertThat(testBlogPostComment.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createBlogPostCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogPostCommentRepository.findAll().size();

        // Create the BlogPostComment with an existing ID
        blogPostComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogPostCommentMockMvc.perform(post("/api/blog-post-comments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPostComment)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPostComment in the database
        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAll();
        assertThat(blogPostCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlogPostComments() throws Exception {
        // Initialize the database
        blogPostCommentRepository.saveAndFlush(blogPostComment);

        // Get all the blogPostCommentList
        restBlogPostCommentMockMvc.perform(get("/api/blog-post-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogPostComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)));
    }
    
    @Test
    @Transactional
    public void getBlogPostComment() throws Exception {
        // Initialize the database
        blogPostCommentRepository.saveAndFlush(blogPostComment);

        // Get the blogPostComment
        restBlogPostCommentMockMvc.perform(get("/api/blog-post-comments/{id}", blogPostComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogPostComment.getId().intValue()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY));
    }

    @Test
    @Transactional
    public void getNonExistingBlogPostComment() throws Exception {
        // Get the blogPostComment
        restBlogPostCommentMockMvc.perform(get("/api/blog-post-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogPostComment() throws Exception {
        // Initialize the database
        blogPostCommentService.save(blogPostComment);

        int databaseSizeBeforeUpdate = blogPostCommentRepository.findAll().size();

        // Update the blogPostComment
        BlogPostComment updatedBlogPostComment = blogPostCommentRepository.findById(blogPostComment.getId()).get();
        // Disconnect from session so that the updates on updatedBlogPostComment are not directly saved in db
        em.detach(updatedBlogPostComment);
        updatedBlogPostComment
            .body(UPDATED_BODY);

        restBlogPostCommentMockMvc.perform(put("/api/blog-post-comments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlogPostComment)))
            .andExpect(status().isOk());

        // Validate the BlogPostComment in the database
        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAll();
        assertThat(blogPostCommentList).hasSize(databaseSizeBeforeUpdate);
        BlogPostComment testBlogPostComment = blogPostCommentList.get(blogPostCommentList.size() - 1);
        assertThat(testBlogPostComment.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogPostComment() throws Exception {
        int databaseSizeBeforeUpdate = blogPostCommentRepository.findAll().size();

        // Create the BlogPostComment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogPostCommentMockMvc.perform(put("/api/blog-post-comments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPostComment)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPostComment in the database
        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAll();
        assertThat(blogPostCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogPostComment() throws Exception {
        // Initialize the database
        blogPostCommentService.save(blogPostComment);

        int databaseSizeBeforeDelete = blogPostCommentRepository.findAll().size();

        // Delete the blogPostComment
        restBlogPostCommentMockMvc.perform(delete("/api/blog-post-comments/{id}", blogPostComment.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAll();
        assertThat(blogPostCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
