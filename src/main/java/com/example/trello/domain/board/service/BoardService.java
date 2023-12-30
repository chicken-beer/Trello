package com.example.trello.domain.board.service;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.board.dto.BoardResponseDto;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.boardUsers.entity.BoardUsers;
import com.example.trello.domain.boardUsers.repository.BoardUsersRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.CustomException;
import com.example.trello.global.s3.S3Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardUsersRepository boardUsersRepository;
    private final S3Utils s3Utils;

    public String createBoard(BoardRequestDto boardRequestDto, User user){
        String filename = s3Utils.uploadFile(boardRequestDto.getBackImg());
        boardRequestDto.setFilename(filename);
        Board board = new Board(boardRequestDto,user);

        boardRepository.save(board);
        boardUsersRepository.save(BoardUsers
                .builder()
                .board(board)
                .user(user)
                .userRole("Admin")
                .build()
        );
        return "보드 생성 성공";
    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new NoSuchElementException("보드를 찾을 수 없습니다.")
        );
        String filename = board.getFilename();
        String imageURL = s3Utils.getFileURL(filename);
        return new BoardResponseDto(board,imageURL);
    }

    public List<BoardResponseDto> getBoardList(User user) {
        List<BoardUsers> boardusersList = boardUsersRepository.findAllByUserId(user.getId());

        return boardusersList.stream()
                .map(boardUsers -> new BoardResponseDto(boardUsers.getBoard(),boardUsers.getBoard().getFilename()))
                .collect(Collectors.toList());
    }

    //TODO 유저정보 받아서 일치하는지 확인하기 (update, delete)
    @Transactional
    public String updateBoard(Long boardId, BoardRequestDto boardRequestDto,User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new NoSuchElementException("보드를 찾을 수 없습니다.")
        );
        BoardUsers boardUsers = boardUsersRepository.findByBoardIdAndUserId(boardId,user.getId());
        if(boardUsers == null){
            throw new CustomException(HttpStatus.CONFLICT,"해당 보드의 멤버가 아닙니다.");
        }
        if(!boardUsers.getUserRole().equals("Admin")){
            throw new CustomException(HttpStatus.CONFLICT,"권한이 없습니다.");
        }
        String filename = board.getFilename();
        s3Utils.deleteFile(filename);

        String newFilename = s3Utils.uploadFile(boardRequestDto.getBackImg());
        boardRequestDto.setFilename(newFilename);
        board.update(boardRequestDto);
        return "보드 수정 완료";
    }

    @Transactional
    public String deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new NoSuchElementException("보드를 찾을 수 없습니다.")
        );

        if(!board.getCreator().equals(user.getLoginId())){
            throw new CustomException(HttpStatus.CONFLICT,"권한이 없습니다.");
        }

        String filename = board.getFilename();
        s3Utils.deleteFile(filename);
        boardRepository.delete(board);

        return "게시물 삭제 완료";
    }
}
