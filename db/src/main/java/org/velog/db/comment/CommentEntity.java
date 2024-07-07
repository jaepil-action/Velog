package org.velog.db.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Comments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {""})
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnore
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<CommentEntity> childCommentList = new ArrayList<>();

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @PreRemove
    public void preRemove(){
        for(CommentEntity comment : childCommentList){
            comment.changeComment(null);
        }
    }

    public void changeComment(CommentEntity commentEntity){
        parentComment = commentEntity;
    }

    public void addParentComment(CommentEntity parentComment){
        this.parentComment = parentComment;
        parentComment.getChildCommentList().add(this);
    }

    public void addPostEntity(PostEntity postEntity){
        this.postEntity = postEntity;
        postEntity.getCommentEntityList().add(this);
    }

    public void editComment(String contents){
        this.contents = contents;
    }
}
