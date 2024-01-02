package com.example.trello.domain.boardUsers.service;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.boardUsers.entity.BoardUsers;
import com.example.trello.domain.boardUsers.repository.BoardUsersRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardUsersService {

    private final BoardUsersRepository boardUsersRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public String inviteUser(Long boardId, String[] usernameList, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new NoSuchElementException("보드를 찾을 수 없습니다.")
        );
        if(!boardUsersRepository.findByBoardIdAndUserId(boardId,user.getId()).getUserRole().equals("Admin") ||
                boardUsersRepository.findByBoardIdAndUserId(boardId, user.getId()) == null){
            throw new CustomException(HttpStatus.CONFLICT,"권한이 없습니다.");
        }

        for(int i=0; i< usernameList.length; i++){
            User invitedUser = userRepository.findByUsername(usernameList[i]).orElseThrow(
                    ()-> new NoSuchElementException("사용자를 찾을 수 없습니다.")
            );
            boardUsersRepository.save(BoardUsers.builder()
                    .board(board)
                    .user(invitedUser)
                    .userRole("invitedMember")
                    .build()
            );
        }
        return "초대가 완료되었습니다.";
    }

    public void responseInvite(Long boardId, String response, User user) {
        BoardUsers boardUsers = boardUsersRepository.findByBoardIdAndUserId(boardId, user.getId());
        if(boardUsers == null){
            throw new CustomException(HttpStatus.CONFLICT,"보드가 삭제됐거나 초대가 취소되었습니다.");
        }
        if(boardUsers.getUserRole().equals("invitedMember")){
            if(response.equals("Accept")){
                boardUsers.updateUserRole("Member");
            } else if(response.equals("Refuse")){
                boardUsersRepository.delete(boardUsers);
            } else{
                throw new CustomException(HttpStatus.CONFLICT,"잘못된 응답입니다.");
            }
        } else{
            throw new CustomException(HttpStatus.CONFLICT, "유효하지 않은 초대입니다.");
        }
    }

    public void changeUserRole(Long boardId, Long userId, String userRole, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new NoSuchElementException("존재하지 않는 보드입니다.")
        );
        if(!board.getCreator().equals(user.getLoginId())){
            throw new CustomException(HttpStatus.CONFLICT, "권한이 없습니다.");
        }

        BoardUsers boardUsers = boardUsersRepository.findByBoardIdAndUserId(boardId,userId);
        boardUsers.updateUserRole(userRole);

    }
}
