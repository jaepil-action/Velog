package org.velog.api.domain.series.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.user.model.UserDto;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@Tag(name = "SeriesApiController", description = "시리즈 Api 서비스 컨트롤러")
public class SeriesApiController {

    private final SeriesBusiness seriesBusiness;

    @Operation(summary = "시리즈 생성 API", description = "사용자블로그의 시리즈 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<SeriesResponse>> createSeries(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Valid @RequestBody SeriesRequest seriesRequest
    ){
        SeriesResponse response = seriesBusiness.register(seriesRequest, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "시리즈 수정 API", description = "사용자 블로그의 시리즈 수정")
    @PutMapping("/{seriesId}")
    public ResponseEntity<Api<String>> editSeries(
            @Parameter(hidden = true) @Login UserDto userDto,
            @PathVariable Long seriesId,
            @Valid @RequestBody SeriesRequest seriesRequest
    ){
        seriesBusiness.edit(userDto, seriesId, seriesRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("시리즈 수정 성공"));
    }

    @Operation(summary = "시리즈 삭제 API", description = "사용자 블로그의 시리즈 수정")
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<Api<String>> deleteSeries(
            @Parameter(hidden = true) @Login UserDto userDto,
            @PathVariable Long seriesId
    ){
        seriesBusiness.delete(userDto, seriesId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("시리즈 삭제 성공"));
    }
}
