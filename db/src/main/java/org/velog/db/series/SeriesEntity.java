package org.velog.db.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    @JsonIgnore
    private BlogEntity blogEntity;

    @Column(length = 255, nullable = false)
    private String title;

    @OneToMany(mappedBy = "seriesEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntities = new ArrayList<>();
}
