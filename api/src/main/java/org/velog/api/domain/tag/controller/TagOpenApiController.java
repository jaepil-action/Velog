package org.velog.api.domain.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.controller.SeriesApiController;
import org.velog.api.domain.series.controller.model.SeriesResponses;
import org.velog.api.domain.tag.business.TagBusiness;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.api.domain.tag.controller.model.TagResponses;
import org.velog.api.utils.hateoas.HateoasTemplate;
import org.velog.api.utils.hateoas.target.TagHateoasLink;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/open-api/tags")
@RequiredArgsConstructor
@Tag(name = "TagOpenApiController", description = "Tag OpenApi 서비스 컨트롤러")
public class TagOpenApiController {

    private final TagBusiness tagBusiness;
    private final HateoasTemplate hateoasTemplate;
    private final TagHateoasLink tagHateoasLink;

    @Operation(summary = "Tag 조회 API", description = "블로그의 Tag 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<EntityModel<TagResponses>>> retrieveAllTag(
            HttpServletRequest request,
            @PathVariable Long blogId
    ){
        TagResponses response = tagBusiness.retrieveAllTag(blogId);
        EntityModel<TagResponses> resource = EntityModel.of(response);

        if(tagBusiness.checkMyTag(request, blogId)){
            WebMvcLinkBuilder add = linkTo(methodOn(TagApiController.class).createTag(null, null));
            WebMvcLinkBuilder edit = linkTo(methodOn(TagApiController.class).editTag(null, null, null));
            WebMvcLinkBuilder delete = linkTo(methodOn(TagApiController.class).deleteTag(null, null));
            resource.add(add.withRel("add-tag"), edit.withRel("edit-tag"), delete.withRel("delete-tag"));
        }else{
            hateoasTemplate.addCommonLinks(resource, request, tagHateoasLink);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
    }
}
