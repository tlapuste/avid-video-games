package avid.video.games.service;

import avid.video.games.domain.BlogPost;
import avid.video.games.web.rest.vm.BlogPostVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BlogPost}.
 */
public interface BlogPostService {

    /**
     * Save a blogPost.
     *
     * @param blogPost the entity to save.
     * @return the persisted entity.
     */
    BlogPost save(BlogPost blogPost);

    /**
     * Get all the blogPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlogPost> findAll(Pageable pageable);

    /**
     * Get the "id" blogPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogPost> findOne(Long id);

    /**
     * Get all the blogPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlogPostVM> findAllPublic(Pageable pageable);

    /**
     * Get the "id" blogPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogPostVM> findPublicOne(Long id);

    /**
     * Delete the "id" blogPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
