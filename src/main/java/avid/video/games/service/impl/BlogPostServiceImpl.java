package avid.video.games.service.impl;

import avid.video.games.domain.BlogPost;
import avid.video.games.domain.BlogPostComment;
import avid.video.games.domain.User;
import avid.video.games.repository.BlogPostCommentRepository;
import avid.video.games.repository.BlogPostRepository;
import avid.video.games.repository.UserRepository;
import avid.video.games.service.BlogPostService;
import avid.video.games.web.rest.errors.BadRequestAlertException;
import avid.video.games.web.rest.vm.BlogPostCommentVM;
import avid.video.games.web.rest.vm.BlogPostVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlogPost}.
 */
@Service
@Transactional
public class BlogPostServiceImpl implements BlogPostService {

    private final Logger log = LoggerFactory.getLogger(BlogPostServiceImpl.class);

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final BlogPostCommentRepository blogPostCommentRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository,
                               UserRepository userRepository,
                               BlogPostCommentRepository blogPostCommentRepository
    ) {
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
        this.blogPostCommentRepository = blogPostCommentRepository;
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
     * Get all the blogPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogPostVM> findAllPublic(Pageable pageable) {
        log.debug("Request to get all BlogPosts");
        return blogPostRepository.findAllPublic(pageable);
    }

    /**
     * Get the "id" blogPostVM.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlogPostVM> findPublicOne(Long id) {
        log.debug("Request to get BlogPost : {}", id);

        BlogPost blogPost = blogPostRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Blog post not be found", "blogPosts", "noPost"));

        BlogPostVM blogPostVM = new BlogPostVM();
        blogPostVM.setTitle(blogPost.getTitle());
        blogPostVM.setBody(blogPost.getBody());

        List<BlogPostComment> blogPostCommentList = blogPostCommentRepository.findAllByComment(blogPost);
        if (!blogPostCommentList.isEmpty()) {
            List<BlogPostCommentVM> comments = blogPostCommentList.stream().map(bpc -> {
                User commenter = bpc.getCommenter();
                if (commenter != null) {
                    BlogPostCommentVM bpcvm = new BlogPostCommentVM();
                    bpcvm.setAuthorName(commenter.getFirstName() + commenter.getLastName());
                    bpcvm.setComment(bpc.getBody());
                    return bpcvm;
                } else {
                    return null;
                }
            }).collect(Collectors.toList());

            blogPostVM.setBlogPostCommentVMS(comments);
        }

        return Optional.ofNullable(blogPostVM);
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
