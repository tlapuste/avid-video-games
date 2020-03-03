package avid.video.games.service.impl;

import avid.video.games.service.BlogPostService;
import avid.video.games.domain.BlogPost;
import avid.video.games.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BlogPost}.
 */
@Service
@Transactional
public class BlogPostServiceImpl implements BlogPostService {

    private final Logger log = LoggerFactory.getLogger(BlogPostServiceImpl.class);

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    /**
     * Save a blogPost.
     *
     * @param blogPost the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlogPost save(BlogPost blogPost) {
        log.debug("Request to save BlogPost : {}", blogPost);
        return blogPostRepository.save(blogPost);
    }

    /**
     * Get all the blogPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogPost> findAll(Pageable pageable) {
        log.debug("Request to get all BlogPosts");
        return blogPostRepository.findAll(pageable);
    }

    /**
     * Get one blogPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlogPost> findOne(Long id) {
        log.debug("Request to get BlogPost : {}", id);
        return blogPostRepository.findById(id);
    }

    /**
     * Delete the blogPost by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogPost : {}", id);
        blogPostRepository.deleteById(id);
    }
}
