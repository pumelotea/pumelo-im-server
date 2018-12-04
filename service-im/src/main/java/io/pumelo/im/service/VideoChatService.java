package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendEntity;
import io.pumelo.data.im.repo.FriendEntityRepo;
import io.pumelo.im.model.Message;
import io.pumelo.im.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VideoChatService {

    @Autowired
    private AuthService authService;
    @Autowired
    private FriendEntityRepo friendEntityRepo;
    @Autowired
    private Sender sender;

    public ApiResponse transferToFriend(String uid,String json) throws Exception {
        FriendEntity byUidAndFriendUid = friendEntityRepo.findByUidAndFriendUid(authService.getId(), uid);
        if (byUidAndFriendUid == null){
            ApiResponse.prompt(IMCode.FRIEND_NOT_EXISTS);
        }
        sender.sendToUser(uid,Message.makeSignalMsg(authService.getId(),uid,json));
        return ApiResponse.prompt(IMCode.SC_OK);
    }
}
