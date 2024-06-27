package org.velog.db.blog;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {"userEntity"})
public class BlogEntity extends BaseEntity {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Column(length = 255, nullable = false)
    private String blogTitle;

    @Column(length = 255)
    private String introduction;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagEntity> tagEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "blogEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeriesEntity> seriesEntityList = new ArrayList<>();

    public void addUserEntity(UserEntity userEntity){
        this.userEntity = userEntity;
        userEntity.setBlogEntity(this); // TODO setter 사용
    }

    public void addRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }

    public void addBlogTitle(String title){
        if(title.isBlank()){
            this.blogTitle = "@" + userEntity.getLoginId();
        }else{
            this.blogTitle = "@" + title;
        }
    }
}
