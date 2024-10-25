package org.velog.api.utils.hateoas.target.other;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.velog.api.domain.comment.controller.CommentApiController;
import org.velog.api.domain.follow.controller.FollowApiController;
import org.velog.api.domain.like.controller.LikeApiController;
import org.velog.api.domain.post.controller.PostApiController;
import org.velog.api.utils.hateoas.target.ifs.HateoasIfs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OtherPostHateoasLink implements HateoasIfs {

    @Override
    public EntityModel<?> getResourceLink(
            EntityModel<?> resource
    ){
        Link addLike = linkTo(methodOn(LikeApiController.class)
                .addLike(null, null)).withRel("addLike");
        Link follow = linkTo(methodOn(FollowApiController.class)
                .userFollow(null, null)).withRel("follow");
        Link addComment = linkTo(methodOn(CommentApiController.class)
                .createComments(null, null, null)).withRel("addComment");

        resource.add(addLike, addComment, follow);

        return  resource;
    }
}
