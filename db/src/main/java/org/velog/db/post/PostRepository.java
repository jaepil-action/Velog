package org.velog.db.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.velog.db.post.enums.PostStatus;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {


    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " join fetch b.userEntity u" +
            " where p.id = :postId")
    Optional<PostEntity> findPostWithAuthorById(@Param("postId") Long postId);


    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " left join fetch p.tagEntity t" +
            " left join fetch p.seriesEntity s" +
            " where p.id = :postId")
    Optional<PostEntity> findPostWithTagAndSeries(@Param("postId") Long postId);

    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " join fetch b.userEntity u")
    Page<PostEntity> findPosts(Pageable pageable);

    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " join fetch b.userEntity u" +
            " WHERE p.postStatus = :postStatus" +
            " Order by p.registrationDate desc ")
    Page<PostEntity> findPostsOrderByDate(Pageable pageable, @Param("postStatus") PostStatus postStatus);

/*    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " join fetch b.userEntity u" +
            " left join p.likeEntityList l" +
            " WHERE p.postStatus = :postStatus" +
            " group by p.id" +
            " order by count(l) desc")*/
@Query("select p from PostEntity p" +
        " join fetch p.blogEntity b" +
        " join fetch b.userEntity u" +
        " WHERE p.postStatus = :postStatus")
    Page<PostEntity> findPostsOrderByLikeCount(Pageable pageable, @Param("postStatus") PostStatus postStatus);

    @Query("SELECT p FROM PostEntity p WHERE p.postStatus IN :statuses")
    Page<PostEntity> findByPostStatusIn(Pageable pageable, @Param("statuses") List<PostStatus> statuses);


}
