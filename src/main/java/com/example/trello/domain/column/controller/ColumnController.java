package com.example.trello.domain.column.controller;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import com.example.trello.global.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards/{boardId}/columns")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> postColumn(@PathVariable Long boardId, @RequestBody ColumnRequestDto requestDto) {
        columnService.postColumn(boardId,requestDto);
        return ResponseEntity.ok().body(new CommonResponseDto("컬럼 생성 성공", HttpStatus.OK.value()));
    }

    @PatchMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> updateColumn(@PathVariable Long boardId,
                                                          @PathVariable Long columnId,
                                                          @RequestBody ColumnRequestDto requestDto) {
        columnService.updateColumn(boardId,columnId,requestDto);
        return ResponseEntity.ok().body(new CommonResponseDto("컬럼 수정 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> deleteColumn(@PathVariable Long boardId,
                                                          @PathVariable Long columnId) {
        columnService.deleteColumn(boardId,columnId);
        return ResponseEntity.ok().body(new CommonResponseDto("컬럼 삭제 성공", HttpStatus.OK.value()));
    }

    @GetMapping("{columnId}")
    public ResponseEntity<ColumnResponseDto> getColumn(@PathVariable Long boardId,
                                                       @PathVariable Long columnId) {
        ColumnResponseDto columnResponseDto = columnService.getColumn(boardId,columnId);
        return ResponseEntity.ok().body(columnResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ColumnResponseDto>> getColumnList(@PathVariable Long boardId) {
        List<ColumnResponseDto> columnResponseDtoList = columnService.getColumnList(boardId);
        return ResponseEntity.ok().body(columnResponseDtoList);
    }
}
