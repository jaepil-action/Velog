package org.velog.db.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.velog.db.BaseEntity;
import org.velog.db.blog.BlogEntity;
import org.velog.db.comment.CommentEntity;
import org.velog.db.follow.FollowEntity;
import org.velog.db.like.LikeEntity;
import org.velog.db.post.enums.PostStatus;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    @JsonIgnore
    private BlogEntity blogEntity;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @Column(length = 255, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 255)
    private String excerpt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    @JsonIgnore
    private SeriesEntity seriesEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    @JsonIgnore
    private TagEntity tagEntity;

    @BatchSize(size = 100)
    @JsonIgnore
    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public void setRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }

    public void addSeriesEntity(SeriesEntity seriesEntity){
        this.seriesEntity = seriesEntity;
        seriesEntity.getPostEntityList().add(this);
    }

    public void addTagEntity(TagEntity tagEntity){
        this.tagEntity = tagEntity;
        tagEntity.getPostEntity().add(this);
    }

    public void addBlogEntity(BlogEntity blogEntity){
        this.blogEntity = blogEntity;
        blogEntity.getPostEntityList().add(this);
    }

    public void changeTagEntity(TagEntity tagEntity){
        this.tagEntity = tagEntity;
    }

    public void changeSeriesEntity(SeriesEntity seriesEntity){
        this.seriesEntity = seriesEntity;
    }

    public void changePost(PostStatus postStatus, String title, String content, TagEntity tagEntity, SeriesEntity seriesEntity, String excerpt) {

        this.postStatus = postStatus;
        this.title = title;
        this.content = content;
        this.tagEntity = tagEntity;
        this.seriesEntity = seriesEntity;
        this.excerpt = excerpt;
    }

}
