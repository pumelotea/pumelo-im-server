package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendEntity;
import io.pumelo.data.im.repo.FriendEntityRepo;
import io.pumelo.im.IMContext;
import io.pumelo.im.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VideoChatService {

    @Autowired
    private AuthService authService;
    @Autowired
    private FriendEntityRepo friendEntityRepo;

    public ApiResponse transferToFriend(String uid,String json) throws IOException {
        FriendEntity byUidAndFriendUid = friendEntityRepo.findByUidAndFriendUid(authService.getId(), uid);
        if (byUidAndFriendUid == null){
            ApiResponse.prompt(IMCode.FRIEND_NOT_EXISTS);
        }
        IMContext.sendToUser(Message.makeSignalMsg(authService.getId(),uid,json));
        return ApiResponse.prompt(IMCode.SC_OK);
    }
}
