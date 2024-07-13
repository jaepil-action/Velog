package org.velog.api.utils.hateoas.target.mine;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.velog.api.domain.follow.controller.FollowApiController;
import org.velog.api.domain.post.controller.PostOpenApiController;
import org.velog.api.domain.series.controller.SeriesApiController;
import org.velog.api.domain.tag.controller.TagOpenApiController;
import org.velog.api.utils.hateoas.target.ifs.HateoasIfs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SeriesHateoasLink implements HateoasIfs {
    @Override
    public EntityModel<?> getResourceLink(EntityModel<?> resource) {

        WebMvcLinkBuilder add = linkTo(methodOn(SeriesApiController.class).createSeries(null, null));
        WebMvcLinkBuilder edit = linkTo(methodOn(SeriesApiController.class).editSeries(null,null, null));
        WebMvcLinkBuilder delete = linkTo(methodOn(SeriesApiController.class).deleteSeries(null,null));

        resource.add(add.withRel("add-series"), edit.withRel("edit-series"), delete.withRel("delete-series"));
        return  resource;
    }
}
