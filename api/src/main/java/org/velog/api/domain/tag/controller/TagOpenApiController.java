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
import org.velog.api.domain.tag.business.TagBusiness;
import org.velog.api.domain.tag.controller.model.TagResponses;
import org.velog.api.utils.hateoas.HateoasTemplate;
import org.velog.api.utils.hateoas.target.mine.TagHateoasLink;
import org.velog.api.utils.hateoas.target.other.OtherTagHateoasLink;

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
    private final OtherTagHateoasLink otherTagHateoasLink;

    @Operation(summary = "Tag 조회 API", description = "블로그의 Tag 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<EntityModel<TagResponses>>> retrieveAllTag(
            HttpServletRequest request,
            @PathVariable Long blogId
    ){
        TagResponses response = tagBusiness.retrieveAllTag(blogId);
        EntityModel<TagResponses> resource = EntityModel.of(response);

        if(tagBusiness.checkMyTag(request, blogId)){
            tagHateoasLink.getResourceLink(resource);
        }else{
            hateoasTemplate.addCommonLinks(resource, request, otherTagHateoasLink);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
    }
}
