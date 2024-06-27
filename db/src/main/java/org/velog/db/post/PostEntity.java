package org.velog.db.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.blog.BlogEntity;
import org.velog.db.follow.FollowEntity;
import org.velog.db.like.LikeEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {"blogEntity", "seriesEntity", "tagEntity"})
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    @JsonIgnore
    private BlogEntity blogEntity;

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

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE)
    private List<LikeEntity> likeEntityList = new ArrayList<>();


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
}
