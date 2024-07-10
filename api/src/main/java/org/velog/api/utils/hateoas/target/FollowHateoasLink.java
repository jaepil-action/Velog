package org.velog.api.utils.hateoas.target;

import lombok.Setter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.velog.api.domain.comment.controller.CommentApiController;
import org.velog.api.domain.follow.controller.FollowApiController;
import org.velog.api.domain.like.controller.LikeApiController;
import org.velog.api.utils.hateoas.target.ifs.HateoasIfs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FollowHateoasLink implements HateoasIfs {
    @Override
    public EntityModel<?> getResourceLink(EntityModel<?> resource) {

        Link follow = linkTo(methodOn(FollowApiController.class)
                .userFollow(null, null)).withRel("follow");
        Link unfollow = linkTo(methodOn(FollowApiController.class)
                .userUnFollow(null, null)).withRel("unFollow");

        resource.add(follow, unfollow);

        return  resource;
    }
}
