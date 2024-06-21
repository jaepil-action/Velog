package org.velog.db.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;;

    @Column(length = 255, nullable = false)
    private String excerpt;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL)
    private List<SeriesEntity> seriesEntity = new ArrayList<>();
}
