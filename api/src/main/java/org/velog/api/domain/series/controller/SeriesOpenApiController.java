package org.velog.api.domain.series.controller;


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
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesResponses;
import org.velog.api.utils.hateoas.HateoasTemplate;
import org.velog.api.utils.hateoas.target.SeriesHateoasLink;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/open-api/series")
@RequiredArgsConstructor
@Tag(name = "SeriesOpenApiController", description = "시리즈 OpenApi 서비스 컨트롤러")
public class SeriesOpenApiController {

    private final SeriesBusiness seriesBusiness;
    private final HateoasTemplate hateoasTemplate;
    private final SeriesHateoasLink seriesHateoasLink;

    @Operation(summary = "시리즈 조회 API", description = "블로그의 시리즈 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<EntityModel<SeriesResponses>>> retrieveAllSeries(
            HttpServletRequest request,
            @PathVariable Long blogId
    ){
        SeriesResponses response = seriesBusiness.retrieveAllSeries(blogId);
        EntityModel<SeriesResponses> resource = EntityModel.of(response);

        if(seriesBusiness.checkMySeries(request, blogId)){
            WebMvcLinkBuilder add = linkTo(methodOn(SeriesApiController.class).createSeries(null, null));
            WebMvcLinkBuilder edit = linkTo(methodOn(SeriesApiController.class).editSeries(null,null, null));
            WebMvcLinkBuilder delete = linkTo(methodOn(SeriesApiController.class).deleteSeries(null,null));
            resource.add(add.withRel("add-series"), edit.withRel("edit-series"), delete.withRel("delete-series"));
        }else{
            hateoasTemplate.addCommonLinks(resource, request, seriesHateoasLink);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
    }
}