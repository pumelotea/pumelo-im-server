package io.pumleo.im.service;

import im.model.APIRespones;
import io.pumelo.common.web.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    /**
     * 创建群组
     * @return
     */
    public ApiResponse createGroup(String groupName){
        return null;
    }

    /**
     * 解散群组
     * @return
     */
    public ApiResponse disbandGroup(String groupId){
        return null;
    }

    /**
     * 获取群组列表
     * @return
     */
    public ApiResponse getGroupList(){
        return null;
    }

    /**
     * 获取群组信息
     * @return
     */
    public ApiResponse getGroupInfo(String groupId){
        return null;
    }

    /**
     * 获取群组成员列表
     * @return
     */
    public ApiResponse getGroupMemberList(String groupId){
        return null;
    }

    /**
     * 获取成员信息,有别于好友信息
     * @return
     */
    public ApiResponse getGroupMember(String groupId,String memberId){
        return null;
    }

    /**
     * 修改群组信息
     * @return
     */
    public ApiResponse updateGroupInfo(String groupId,String groupName){
        return null;
    }

    /**
     * 主动退出群组
     * @return
     */
    public ApiResponse exitGroup(String groupId){
        return null;
    }

    /**
     * 申请加入群组
     * @return
     */
    public ApiResponse askJoinGroup(String groupId,String reason){
        return null;
    }

    /**
     * 审核待加入群组的用户
     * @return
     */
    public ApiResponse reviewJoinUser(String groupAskId,Boolean isAgree){
        return null;
    }

    /**
     * 获取申请列表
     * @return
     */
    public ApiResponse getReviewAskJoinGroupList(){
        return null;
    }

    /**
     * 删除组内成员(T人)
     * @return
     */
    public ApiResponse deleteMember(String groupId,String memberId){
        return null;
    }


    public APIRespones search() {
        return null;
    }

    /**
     * 备注好友
     * @return
     */
    public ApiResponse remarkGroup(String groupId,String remark){
        return null;
    }

}
