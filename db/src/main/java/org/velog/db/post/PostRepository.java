package org.velog.db.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p" +
            " left join fetch p.blogEntity b" +
            " left join fetch p.tagEntity t" +
            " left join fetch p.seriesEntity s" +
            " where p.id = :postId")
    Optional<PostEntity> findPostWithTagAndSeries(@Param("postId") Long postId);


    @Query("select p from PostEntity p" +
            " left join fetch p.blogEntity b" +
            " left join fetch p.seriesEntity s" +
            " where p.id = :postId")
    Optional<PostEntity> findPostWithSeries(@Param("postId") Long postId);

    @Query("select p from PostEntity p" +
            " left join fetch p.blogEntity b" +
            " left join fetch p.tagEntity t" +
            " where p.id = :postId")
    Optional<PostEntity> findPostWithTag(@Param("postId") Long postId);

}
