package org.velog.db.like;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {"userEntity", "postEntity"})
public class LikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    public void addRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }
}
