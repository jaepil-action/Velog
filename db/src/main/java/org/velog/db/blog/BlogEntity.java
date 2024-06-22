package org.velog.db.blog;

import jakarta.persistence.*;
import lombok.*;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/***
 * 1. 게시글 CRUD
 * 2. /도메인/@login_id 특정 유저의 블로그 상세페이지
 * 3. 게시글 좋아요, 작성일 기준으로 정렬
 * 4.
 */
public class BlogEntity {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagEntity> tagEntities = new ArrayList<>();

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntities = new ArrayList<>();

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeriesEntity> seriesEntities = new ArrayList<>();

    @Column(length = 255)
    private String introduction;
}
