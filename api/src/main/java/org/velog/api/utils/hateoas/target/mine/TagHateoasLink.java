package org.velog.api.utils.hateoas.target.mine;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.velog.api.domain.post.controller.PostOpenApiController;
import org.velog.api.domain.series.controller.SeriesApiController;
import org.velog.api.domain.series.controller.SeriesOpenApiController;
import org.velog.api.domain.tag.controller.TagApiController;
import org.velog.api.domain.tag.controller.TagOpenApiController;
import org.velog.api.utils.hateoas.target.ifs.HateoasIfs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoasLink implements HateoasIfs {
    @Override
    public EntityModel<?> getResourceLink(EntityModel<?> resource) {

        WebMvcLinkBuilder add = linkTo(methodOn(TagApiController.class).createTag(null, null));
        WebMvcLinkBuilder edit = linkTo(methodOn(TagApiController.class).editTag(null, null, null));
        WebMvcLinkBuilder delete = linkTo(methodOn(TagApiController.class).deleteTag(null, null));

        resource.add(add.withRel("add-tag"), edit.withRel("edit-tag"), delete.withRel("delete-tag"));
        return  resource;
    }
}
