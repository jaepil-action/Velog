package org.velog.api.utils.hateoas.target.mine;

import lombok.Setter;
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
public class PostHateoasLink implements HateoasIfs {

    @Override
    public EntityModel<?> getResourceLink(
            EntityModel<?> resource
    ){
        WebMvcLinkBuilder edit = linkTo(methodOn(PostApiController.class).editPost(null,null, null));
        WebMvcLinkBuilder delete = linkTo(methodOn(PostApiController.class).deletePost(null,null));
        resource.add(edit.withRel("edit-post"), delete.withRel("delete-post"));

        return  resource;
    }
}
