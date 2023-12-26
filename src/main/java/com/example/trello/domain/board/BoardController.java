package com.example.trello.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public String createBoard(@RequestBody BoardRequestDto boardRequestDto){
        try {
            return boardService.createBoard(boardRequestDto);
        } catch (IllegalArgumentException e){
            return e.getMessage();
        }

    }

    @GetMapping("/{boardId}")
    public Object getBoard(@PathVariable Long boardId){
        try {
            return boardService.getBoard(boardId);
        } catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }


    @GetMapping("")
    public List<BoardResponseDto> getBoardList(){

        return boardService.getBoardList();
    }

    //TODO 유저정보 받기 (update, delete)
    @PatchMapping("/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto){
        try {
            return boardService.updateBoard(boardId,boardRequestDto);
        } catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){
        try {
            return boardService.deleteBoard(boardId);
        } catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }

    @PostMapping("/{boardId}/users/{userId}")
    public void inviteUser(@PathVariable Long boardId, @PathVariable Long userId){
        boardService.inviteUser(boardId, userId);
    }
}
