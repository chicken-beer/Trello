package com.example.trello.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public String createBoard(BoardRequestDto boardRequestDto) {
        if(boardRequestDto.getTitle().isEmpty()){
            return "제목을 반드시 입력하세요.";
        }

        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        return "보드 생성 성공";
    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("보드를 찾을 수 없습니다.")
        );

        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();

        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    //TODO 유저정보 받아서 일치하는지 확인하기 (update, delete)
    public String updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("보드를 찾을 수 없습니다.")
        );

        board.update(boardRequestDto);
        return "보드 수정 완료";
    }

    public String deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("보드를 찾을 수 없습니다.")
        );

        boardRepository.delete(board);
        return "게시물 삭제 완료";
    }

    public void inviteUser(Long boardId, Long userId) {

    }
}
