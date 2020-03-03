package avid.video.games.web.rest;

import avid.video.games.domain.BlogPostComment;
import avid.video.games.service.BlogPostCommentService;
import avid.video.games.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link avid.video.games.domain.BlogPostComment}.
 */
@RestController
@RequestMapping("/api")
public class BlogPostCommentResource {

    private final Logger log = LoggerFactory.getLogger(BlogPostCommentResource.class);

    private static final String ENTITY_NAME = "blogPostComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogPostCommentService blogPostCommentService;

    public BlogPostCommentResource(BlogPostCommentService blogPostCommentService) {
        this.blogPostCommentService = blogPostCommentService;
    }

    /**
     * {@code POST  /blog-post-comments} : Create a new blogPostComment.
     *
     * @param blogPostComment the blogPostComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogPostComment, or with status {@code 400 (Bad Request)} if the blogPostComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-post-comments")
    public ResponseEntity<BlogPostComment> createBlogPostComment(@RequestBody BlogPostComment blogPostComment) throws URISyntaxException {
        log.debug("REST request to save BlogPostComment : {}", blogPostComment);
        if (blogPostComment.getId() != null) {
            throw new BadRequestAlertException("A new blogPostComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogPostComment result = blogPostCommentService.save(blogPostComment);
        return ResponseEntity.created(new URI("/api/blog-post-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-post-comments} : Updates an existing blogPostComment.
     *
     * @param blogPostComment the blogPostComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogPostComment,
     * or with status {@code 400 (Bad Request)} if the blogPostComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogPostComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-post-comments")
    public ResponseEntity<BlogPostComment> updateBlogPostComment(@RequestBody BlogPostComment blogPostComment) throws URISyntaxException {
        log.debug("REST request to update BlogPostComment : {}", blogPostComment);
        if (blogPostComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlogPostComment result = blogPostCommentService.save(blogPostComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blogPostComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blog-post-comments} : get all the blogPostComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogPostComments in body.
     */
    @GetMapping("/blog-post-comments")
    public ResponseEntity<List<BlogPostComment>> getAllBlogPostComments(Pageable pageable) {
        log.debug("REST request to get a page of BlogPostComments");
        Page<BlogPostComment> page = blogPostCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blog-post-comments/:id} : get the "id" blogPostComment.
     *
     * @param id the id of the blogPostComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogPostComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-post-comments/{id}")
    public ResponseEntity<BlogPostComment> getBlogPostComment(@PathVariable Long id) {
        log.debug("REST request to get BlogPostComment : {}", id);
        Optional<BlogPostComment> blogPostComment = blogPostCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogPostComment);
    }

    /**
     * {@code DELETE  /blog-post-comments/:id} : delete the "id" blogPostComment.
     *
     * @param id the id of the blogPostComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-post-comments/{id}")
    public ResponseEntity<Void> deleteBlogPostComment(@PathVariable Long id) {
        log.debug("REST request to delete BlogPostComment : {}", id);
        blogPostCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
