package org.velog.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    private LocalDateTime registrationDate;

    /***
     * TODO : @preUpdate, @prePersist
     *        헤테오스, @Parameter
     *        상태 (임시글, 비공개, 공개)
     *        프로필, @Data 제거후 @Getter
     *
     */
}
