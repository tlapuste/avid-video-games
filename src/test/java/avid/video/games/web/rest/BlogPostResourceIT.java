package avid.video.games.web.rest;

import avid.video.games.AvidApp;
import avid.video.games.domain.BlogPost;
import avid.video.games.repository.BlogPostRepository;
import avid.video.games.service.BlogPostService;
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
 * Integration tests for the {@link BlogPostResource} REST controller.
 */
@SpringBootTest(classes = AvidApp.class)
public class BlogPostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostService blogPostService;

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

    private MockMvc restBlogPostMockMvc;

    private BlogPost blogPost;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogPostResource blogPostResource = new BlogPostResource(blogPostService);
        this.restBlogPostMockMvc = MockMvcBuilders.standaloneSetup(blogPostResource)
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
    public static BlogPost createEntity(EntityManager em) {
        BlogPost blogPost = new BlogPost()
            .title(DEFAULT_TITLE)
            .body(DEFAULT_BODY);
        return blogPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPost createUpdatedEntity(EntityManager em) {
        BlogPost blogPost = new BlogPost()
            .title(UPDATED_TITLE)
            .body(UPDATED_BODY);
        return blogPost;
    }

    @BeforeEach
    public void initTest() {
        blogPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogPost() throws Exception {
        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();

        // Create the BlogPost
        restBlogPostMockMvc.perform(post("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isCreated());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate + 1);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlogPost.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createBlogPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();

        // Create the BlogPost with an existing ID
        blogPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogPostMockMvc.perform(post("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlogPosts() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList
        restBlogPostMockMvc.perform(get("/api/blog-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)));
    }
    
    @Test
    @Transactional
    public void getBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get the blogPost
        restBlogPostMockMvc.perform(get("/api/blog-posts/{id}", blogPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogPost.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY));
    }

    @Test
    @Transactional
    public void getNonExistingBlogPost() throws Exception {
        // Get the blogPost
        restBlogPostMockMvc.perform(get("/api/blog-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogPost() throws Exception {
        // Initialize the database
        blogPostService.save(blogPost);

        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Update the blogPost
        BlogPost updatedBlogPost = blogPostRepository.findById(blogPost.getId()).get();
        // Disconnect from session so that the updates on updatedBlogPost are not directly saved in db
        em.detach(updatedBlogPost);
        updatedBlogPost
            .title(UPDATED_TITLE)
            .body(UPDATED_BODY);

        restBlogPostMockMvc.perform(put("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlogPost)))
            .andExpect(status().isOk());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogPost.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Create the BlogPost

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogPostMockMvc.perform(put("/api/blog-posts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogPost() throws Exception {
        // Initialize the database
        blogPostService.save(blogPost);

        int databaseSizeBeforeDelete = blogPostRepository.findAll().size();

        // Delete the blogPost
        restBlogPostMockMvc.perform(delete("/api/blog-posts/{id}", blogPost.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
