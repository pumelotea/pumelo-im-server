package io.pumelo.im.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.GroupAskJoinEntity;
import io.pumelo.data.im.entity.GroupEntity;
import io.pumelo.data.im.entity.GroupMemberEntity;
import io.pumelo.im.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @AuthFilter
    @PostMapping("/group")
    public ApiResponse<GroupEntity> createGroup(@RequestParam String groupName) {
       return groupService.createGroup(groupName);
    }

    /**
     * 解散群组
     *
     * @return
     */
    @AuthFilter
    @DeleteMapping("/group/{groupId}")
    public ApiResponse disbandGroup(@PathVariable String groupId) {
        return groupService.disbandGroup(groupId);
    }

    /**
     * 获取群组列表
     * 群不一定是自己创建的
     * @return
     */
    @AuthFilter
    @GetMapping("/groups")
    public ApiResponse<List<GroupEntity>> getGroupList() {
        return groupService.getGroupList();
    }

    /**
     * 获取群组信息
     *
     * @return
     */
    @AuthFilter
    @GetMapping("/group/{groupId}")
    public ApiResponse<GroupEntity> getGroupInfo(@PathVariable String groupId) {
        return groupService.getGroupInfo(groupId);
    }

    /**
     * 获取群组成员列表
     *
     * @return
     */
    @AuthFilter
    @GetMapping("/group/{groupId}/members")
    public ApiResponse<List<GroupMemberEntity>> getGroupMemberList(@PathVariable String groupId) {
        return groupService.getGroupMemberList(groupId);
    }

    /**
     * 获取成员信息,有别于好友信息
     *
     * @return
     */
    @AuthFilter
    @GetMapping("/group/{groupId}/member/{uid}")
    public ApiResponse<GroupMemberEntity> getGroupMember(@PathVariable String groupId, @PathVariable String uid) {
        return groupService.getGroupMember(groupId, uid);
    }

    /**
     * 修改群组信息
     *
     * @return
     */
    @AuthFilter
    @PutMapping("/group/{groupId}/group_name")
    public ApiResponse updateGroupInfo(@PathVariable String groupId, @RequestParam String groupName) {
        return groupService.updateGroupInfo(groupId, groupName);
    }

    /**
     * 主动退出群组
     *
     * @return
     */
    @AuthFilter
    @DeleteMapping("/group/{groupId}/member/self")
    public ApiResponse exitGroup(@PathVariable String groupId) {
        return groupService.exitGroup(groupId);
    }

    /**
     * 申请加入群组
     *
     * @return
     */
    @AuthFilter
    @PostMapping("/ask_group")
    public ApiResponse askJoinGroup(@RequestParam String groupId,@RequestParam String reason) {
        return groupService.askJoinGroup(groupId, reason);
    }

    /**
     * 审核待加入群组的用户
     *
     * @return
     */
    @AuthFilter
    @PutMapping("/ask_group/{groupAskId}")
    public ApiResponse reviewJoinUser(@PathVariable String groupAskId,@RequestParam Boolean isAgree) {
        return groupService.reviewJoinUser(groupAskId, isAgree);
    }

    /**
     * 获取申请列表
     *
     * @return
     */
    @AuthFilter
    @GetMapping("/ask_groups")
    public ApiResponse<List<GroupAskJoinEntity>> getReviewAskJoinGroupList() {
        return groupService.getReviewAskJoinGroupList();
    }

    /**
     * 删除组内成员(T人)
     *
     * @return
     */
    @AuthFilter
    @DeleteMapping("/group/{groupId}/member/{uid}")
    public ApiResponse deleteMember(@PathVariable String groupId,@PathVariable String uid) {
        return groupService.deleteMember(groupId, uid);
    }


    /**
     * 备注群组
     *
     * @return
     */
    @AuthFilter
    @PutMapping("/group/{groupId}/remark")
    public ApiResponse remarkGroup(@PathVariable String groupId,@RequestParam String remark) {
        return groupService.remarkGroup(groupId, remark);
    }
}
