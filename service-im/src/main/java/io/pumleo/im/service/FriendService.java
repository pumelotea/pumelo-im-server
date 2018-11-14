package io.pumleo.im.service;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.repo.FriendEntityRepo;
import io.pumelo.data.im.repo.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    private FriendEntityRepo friendEntityRepo;

    @Autowired
    private UserEntityRepo userEntityRepo;

    /**
     * 申请加好友
     * @return
     */
    public ApiResponse askAddFriend(String friendUid){
        return null;
    }

    /**
     * 审核好友申请
     * @return
     */
    public ApiResponse reviewAskFriend(String friendAskId){
        return null;
    }

    /**
     * 获取申请列表
     * @return
     */
    public ApiResponse getReviewAskList(){
        return null;
    }

    /**
     * 删除好友
     * @return
     */
    public ApiResponse deleteFriend(String friendUid){
        return null;
    }



    /**
     * 获取好友列表
     * @return
     */
    public ApiResponse getFriendList(){
        return null;
    }

    /**
     * 获取单个好友详情
     * @return
     */
    public ApiResponse getFriend(String uid){
        return null;
    }


    /**
     * 备注好友
     * @return
     */
    public ApiResponse remarkFriend(String uid,String remark){
        return null;
    }


}
