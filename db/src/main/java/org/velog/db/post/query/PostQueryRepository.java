package org.velog.db.post.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.velog.db.post.PostEntity;
import org.velog.db.post.enums.PostStatus;

/**
 *     private Long postId;
 *     private String title;
 *     private String excerpt;
 *     private AuthorDto author;
 *     private int likeCount;
 */
public interface PostQueryRepository extends JpaRepository<PostEntity, Long> {

    @Query("select new org.velog.db.post.query.PostQueryDto(p.id, p.title, p.excerpt, count(l))" +
            " from PostEntity p" +
            " left join p.likeEntityList l" +
            " where p.postStatus = :postStatus" +
            " group by p.id" +
            " order by count(l) desc")
    Page<PostQueryDto> findPostsOrderByLikeCount(Pageable pageable, @Param("postStatus") PostStatus postStatus);
}
