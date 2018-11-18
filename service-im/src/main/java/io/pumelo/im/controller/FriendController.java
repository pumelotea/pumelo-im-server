package io.pumelo.im.controller;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendAskEntity;
import io.pumelo.data.im.vo.friend.FriendVo;
import io.pumelo.im.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;

    public ApiResponse askAddFriend(String targetUid, String reason){
        return friendService.askAddFriend(targetUid, reason);
    }

    public ApiResponse reviewAskFriend(String friendAskId,Boolean isAgree){
        return friendService.reviewAskFriend(friendAskId, isAgree);
    }

    public ApiResponse<List<FriendAskEntity>> getReviewAskList(){
        return friendService.getReviewAskList();
    }

    public ApiResponse deleteFriend(String friendUid,Boolean isBoth){
        return friendService.deleteFriend(friendUid, isBoth);
    }

    public ApiResponse<List<FriendVo>> getFriendList(){
        return friendService.getFriendList();
    }

    public ApiResponse<FriendVo> getFriend(String uid){
        return friendService.getFriend(uid);
    }

    public ApiResponse<FriendVo> remarkFriend(String uid,String remark){
        return friendService.remarkFriend(uid, remark);
    }
}
