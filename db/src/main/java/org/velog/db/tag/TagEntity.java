package org.velog.db.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {"blogEntity"})
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    @JsonIgnore
    private BlogEntity blogEntity;

    @Column(length = 255, nullable = false)
    private String title;

    private Integer count;

    @OneToMany(mappedBy = "tagEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntity = new ArrayList<>();

    public void setRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }

    public void addBlogEntity(BlogEntity blogEntity){
        this.blogEntity = blogEntity;
        blogEntity.getTagEntityList().add(this);
    }

    public void setCountZero(){
        count = 0;
    }
}
