package org.velog.api.utils.hateoas.target.other;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.velog.api.domain.post.controller.PostOpenApiController;
import org.velog.api.domain.series.controller.SeriesOpenApiController;
import org.velog.api.utils.hateoas.target.ifs.HateoasIfs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OtherTagHateoasLink implements HateoasIfs {
    @Override
    public EntityModel<?> getResourceLink(EntityModel<?> resource) {

        Link posts = linkTo(methodOn(PostOpenApiController.class)
                .retrieveAllPost(null, 0,10, null)).withRel("retrieveAllPosts");
        Link series = linkTo(methodOn(SeriesOpenApiController.class)
                .retrieveAllSeries(null, null)).withRel("retrieveAllSeries");

        resource.add(posts, series);
        return  resource;
    }
}
