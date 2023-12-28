package com.example.trello.domain.column.controller;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import com.example.trello.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards/{boardId}/columns")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<ApiResponse> postColumn(@PathVariable Long boardId, @RequestBody ColumnRequestDto requestDto) {
        columnService.postColumn(boardId,requestDto);
        return ResponseEntity.ok( ApiResponse.ok( "컬럼 생성 성공" ) );
    }

    @PatchMapping("/{columnId}")
    public ResponseEntity<ApiResponse> updateColumnName(@PathVariable Long boardId,
                                                              @PathVariable Long columnId,
                                                              @RequestBody ColumnRequestDto requestDto) {
        columnService.updateColumnName(boardId,columnId,requestDto);
        return ResponseEntity.ok( ApiResponse.ok( "컬럼 이름 수정 성공" ) );
    }

    @PatchMapping("/{columnId}/order/{columnOrder}")
    public ResponseEntity<ApiResponse> updateColumnOrder(@PathVariable Long boardId,
                                                               @PathVariable Long columnId,
                                                               @PathVariable Integer columnOrder) {
        columnService.updateColumnOrder(boardId,columnId,columnOrder);
        return ResponseEntity.ok( ApiResponse.ok( "컬럼 순서 수정 성공" ) );
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<ApiResponse> deleteColumn(@PathVariable Long boardId,
                                                          @PathVariable Long columnId) {
        columnService.deleteColumn(boardId,columnId);
        return ResponseEntity.ok( ApiResponse.ok( "컬럼 삭제 성공" ) );
    }

    @GetMapping("{columnId}")
    public ResponseEntity<ApiResponse> getColumn(@PathVariable Long boardId,
                                                       @PathVariable Long columnId) {
        ColumnResponseDto columnResponseDto = columnService.getColumn(boardId,columnId);
        return ResponseEntity.ok( ApiResponse.ok( columnResponseDto ) );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getColumnList(@PathVariable Long boardId) {
        List<ColumnResponseDto> columnResponseDtoList = columnService.getColumnList(boardId);
        return ResponseEntity.ok( ApiResponse.ok( columnResponseDtoList ) );
    }
}
