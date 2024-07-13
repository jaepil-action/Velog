package org.velog.db.log;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;

@Entity
@Getter
@Table(name = "logs")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class LogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    private String methodName;
    private long executionTime;

    public LogEntity(String methodName, long executionTime) {
        this.methodName = methodName;
        this.executionTime = executionTime;
    }

    public void changeExecutionTime(long executionTime){
        this.executionTime = executionTime;
    }
}
