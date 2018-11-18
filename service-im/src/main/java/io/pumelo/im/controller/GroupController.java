package io.pumelo.im.controller;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.GroupAskJoinEntity;
import io.pumelo.data.im.entity.GroupEntity;
import io.pumelo.data.im.entity.GroupMemberEntity;
import io.pumelo.im.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    /**
     * 创建群组
     *
     * @return
     */
    public ApiResponse<GroupEntity> createGroup(String groupName) {
       return groupService.createGroup(groupName);
    }

    /**
     * 解散群组
     *
     * @return
     */
    public ApiResponse disbandGroup(String groupId) {
        return groupService.disbandGroup(groupId);
    }

    /**
     * 获取群组列表
     * 群不一定是自己创建的
     * @return
     */
    public ApiResponse<List<GroupEntity>> getGroupList() {
        return groupService.getGroupList();
    }

    /**
     * 获取群组信息
     *
     * @return
     */
    public ApiResponse<GroupEntity> getGroupInfo(String groupId) {
        return groupService.getGroupInfo(groupId);
    }

    /**
     * 获取群组成员列表
     *
     * @return
     */
    public ApiResponse<List<GroupMemberEntity>> getGroupMemberList(String groupId) {
        return groupService.getGroupMemberList(groupId);
    }

    /**
     * 获取成员信息,有别于好友信息
     *
     * @return
     */
    public ApiResponse<GroupMemberEntity> getGroupMember(String groupId, String uid) {
        return groupService.getGroupMember(groupId, uid);
    }

    /**
     * 修改群组信息
     *
     * @return
     */
    public ApiResponse updateGroupInfo(String groupId, String groupName) {
        return groupService.updateGroupInfo(groupId, groupName);
    }

    /**
     * 主动退出群组
     *
     * @return
     */
    public ApiResponse exitGroup(String groupId) {
        return groupService.exitGroup(groupId);
    }

    /**
     * 申请加入群组
     *
     * @return
     */
    public ApiResponse askJoinGroup(String groupId, String reason) {
        return groupService.askJoinGroup(groupId, reason);
    }

    /**
     * 审核待加入群组的用户
     *
     * @return
     */
    public ApiResponse reviewJoinUser(String groupAskId, Boolean isAgree) {
        return groupService.reviewJoinUser(groupAskId, isAgree);
    }

    /**
     * 获取申请列表
     *
     * @return
     */
    public ApiResponse<List<GroupAskJoinEntity>> getReviewAskJoinGroupList() {
        return groupService.getReviewAskJoinGroupList();
    }

    /**
     * 删除组内成员(T人)
     *
     * @return
     */
    public ApiResponse deleteMember(String groupId, String uid) {
        return groupService.deleteMember(groupId, uid);
    }


    /**
     * 备注群组
     *
     * @return
     */
    public ApiResponse remarkGroup(String groupId, String remark) {
        return groupService.remarkGroup(groupId, remark);
    }
}
