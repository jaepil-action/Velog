package org.velog.api.domain.series.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@Tag(name = "SeriesApiController", description = "시리즈 Api 서비스 컨트롤러")
public class SeriesController {

    private final SeriesBusiness seriesBusiness;

    @Operation(summary = "시리즈 생성 API", description = "사용자블로그의 시리즈 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<SeriesResponse>> createSeries(
            HttpServletRequest request,
            @Valid @RequestBody SeriesRegisterRequest seriesRegisterRequest
    ){
        SeriesResponse response = seriesBusiness.register(seriesRegisterRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }
}
