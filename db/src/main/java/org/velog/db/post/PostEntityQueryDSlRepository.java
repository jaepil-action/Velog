package org.velog.db.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.velog.db.post.model.PostSearchDto;

import java.util.List;

import static org.velog.db.blog.QBlogEntity.*;
import static org.velog.db.post.QPostEntity.*;
import static org.velog.db.user.QUserEntity.*;

@Repository
public class PostEntityQueryDSlRepository {

    private final JPAQueryFactory query;
    @Autowired
    public PostEntityQueryDSlRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<PostSearchDto> searchPostDto(
            String loginIdCond,
            String titleCond
    ){
        List<PostSearchDto> result = query
                //.select(userEntity.loginId, postEntity.title, postEntity.postStatus) -> 페치조인 성능최적화O
                // DTO로 반환하면 페치조인을 하면 안해도됨, 하면 에러발생 (페치조인시 루트엔티티가 셀렉절에 무조건포함필수)
                .select(Projections.constructor(PostSearchDto.class,
                        userEntity.loginId,
                        postEntity.title,
                        postEntity.postStatus))
                .from(postEntity)
                .join(postEntity.blogEntity, blogEntity)
                .join(blogEntity.userEntity, userEntity)
                .where(loginIdAndTitleSearchCond(loginIdCond, titleCond))
                .fetch();

        return result;
    }

    private BooleanExpression loginIdSearchCond(String loginIdCond) {
        return loginIdCond != null ? userEntity.loginId.like("%" + loginIdCond + "%") : null;
    }

    private BooleanExpression titleSearchCond(String titleCond) {
        return titleCond != null ? postEntity.title.like("%" + titleCond + "%") : null;
    }


    private BooleanExpression loginIdAndTitleSearchCond(String loginIdCond, String titleCond) {
        BooleanExpression loginIdExp = loginIdSearchCond(loginIdCond);
        BooleanExpression titleExp = titleSearchCond(titleCond);

        if(loginIdExp != null && titleExp != null){
            return loginIdExp.and(titleExp);
        }else if(loginIdExp != null){
            return loginIdExp;
        }else if(titleExp != null){
            return titleExp;
        }else{
            return null;
        }
    }
}
