package org.velog.api.domain.post.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.*;
import org.velog.api.utils.hateoas.HateoasTemplate;
import org.velog.api.utils.hateoas.target.mine.PostHateoasLink;
import org.velog.api.utils.hateoas.target.other.OtherPostHateoasLink;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/open-api/posts")
@RequiredArgsConstructor
@Tag(name = "PostOpenApiController", description = "Post OpenApi 서비스 컨트롤러")
public class PostOpenApiController {

    private final PostBusiness postBusiness;
    private final HateoasTemplate hateoasTemplate;
    private final PostHateoasLink myPostHateoasLink;
    private final OtherPostHateoasLink otherPostHateoasLink;

    @Operation(summary = "Post 단건 조회 API", description = "PostId 입력")
    @GetMapping("{postId}")
    public ResponseEntity<Api<EntityModel<PostDetailResponse>>> retrievePost(
            @Parameter(description = "조회할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            HttpServletRequest request
    ){
        PostDetailResponse postResponse = postBusiness.getPost(postId);
        EntityModel<PostDetailResponse> resource = EntityModel.of(postResponse);
        WebMvcLinkBuilder all = linkTo(methodOn(this.getClass()).retrieveAllPost(null,0, 10, null));
        resource.add(all.withRel("all-posts"));

        if(postBusiness.checkMyPost(request, postResponse)){
            myPostHateoasLink.getResourceLink(resource);
        }else{
            hateoasTemplate.addCommonLinks(resource, request, otherPostHateoasLink);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
    }

    @Operation(summary = "모든 Post 상세 조회 API", description = "최신순, 인기순 조회 가능 (비공개 조회불가)")
    @GetMapping("")
    public ResponseEntity<Api<EntityModel<PostsDetailPageResponse>>> retrieveAllPost(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sortCond
    ){
        PostsDetailPageResponse postResponseList = postBusiness.getDetailPosts(page, size, sortCond);

        EntityModel<PostsDetailPageResponse> resource = EntityModel.of(postResponseList);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePost(null, null));
        resource.add(link.withRel("retrievePost"));
        hateoasTemplate.addCommonLinks(resource, request, myPostHateoasLink);

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
    }

    @Operation(summary = "모든 Post 임시 조회 API", description = "공개,임시 상태 조회 가능 (비공개 조회불가)")
    @GetMapping("/drafts")
    public ResponseEntity<Api<PostsPageResponse>> retrieveAllPostWithDraft(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PostsPageResponse postResponseList = postBusiness.getPostsByStatus(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(postResponseList));
    }


    @Operation(summary = "Post 댓글 갯수 조회 API", description = "PostId 입력")
    @GetMapping("/{postId}/commentCount")
    public ResponseEntity<Api<Integer>> editSeries(
            @Parameter(description = "댓글 갯수 해당 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        Integer count = postBusiness.commentCountByPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(count));
    }
}
