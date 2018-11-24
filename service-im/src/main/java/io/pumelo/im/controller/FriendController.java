package io.pumelo.im.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendAskEntity;
import io.pumelo.data.im.vo.askFriend.AskFriendVo;
import io.pumelo.data.im.vo.friend.FriendVo;
import io.pumelo.im.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;

    @AuthFilter
    @PostMapping("/ask_friend")
    public ApiResponse askAddFriend(@RequestParam String targetUid, @RequestParam String reason) throws IOException {
        return friendService.askAddFriend(targetUid, reason);
    }

    @AuthFilter
    @PutMapping("/ask_friend/{friendAskId}")
    public ApiResponse reviewAskFriend(@PathVariable String friendAskId, @RequestParam Boolean isAgree) throws IOException {
        return friendService.reviewAskFriend(friendAskId, isAgree);
    }

    @AuthFilter
    @GetMapping("/ask_friends")
    public ApiResponse<List<AskFriendVo>> getReviewAskList() {
        return friendService.getReviewAskList();
    }

    @AuthFilter
    @DeleteMapping("/friend/{friendUid}/{isBoth}")
    public ApiResponse deleteFriend(@PathVariable String friendUid, @PathVariable Boolean isBoth) {
        return friendService.deleteFriend(friendUid, isBoth);
    }

    @AuthFilter
    @GetMapping("/friends")
    public ApiResponse<List<FriendVo>> getFriendList() {
        return friendService.getFriendList();
    }

    @AuthFilter
    @GetMapping("/friend/{uid}")
    public ApiResponse<FriendVo> getFriend(@PathVariable String uid) {
        return friendService.getFriend(uid);
    }

    @AuthFilter
    @PutMapping("/friend/{uid}/remark")
    public ApiResponse<FriendVo> remarkFriend(@PathVariable String uid, @RequestParam String remark) {
        return friendService.remarkFriend(uid, remark);
    }
}
