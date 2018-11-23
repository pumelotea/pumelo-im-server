package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.GroupAskJoinEntity;
import io.pumelo.data.im.entity.GroupEntity;
import io.pumelo.data.im.entity.GroupMemberEntity;
import io.pumelo.data.im.repo.GroupAskJoinEntityRepo;
import io.pumelo.data.im.repo.GroupEntityRepo;
import io.pumelo.data.im.repo.GroupMemberEntityRepo;
import io.pumelo.data.im.repo.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupAskJoinEntityRepo groupAskJoinEntityRepo;
    @Autowired
    private GroupEntityRepo groupEntityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private GroupMemberEntityRepo groupMemberEntityRepo;
    @Autowired
    private AuthService authService;


    /**
     * 创建群组
     *
     * @return
     */
    @Transactional
    public ApiResponse<GroupEntity> createGroup(String groupName) {
        GroupEntity groupEntity = GroupEntity.create(groupName, authService.getId());
        groupEntity = groupEntityRepo.save(groupEntity);
        //创建完成后，把自己加入组
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity(groupEntity.getGroupId(), authService.getId());
        groupMemberEntityRepo.save(groupMemberEntity);
        return ApiResponse.ok(groupEntity);
    }

    /**
     * 解散群组
     *
     * @return
     */
    @Transactional
    public ApiResponse disbandGroup(String groupId) {
        //查自己创建的群组
        GroupEntity groupEntity = groupEntityRepo.findByUid(authService.getId(), groupId);
        if (groupEntity == null) {
            return ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        groupEntity.setTrash(true);
        groupEntityRepo.save(groupEntity);
        //删除全部成员
        groupMemberEntityRepo.trashByGroupIdAndUid(groupId);
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 获取群组列表
     * 群不一定是自己创建的
     * @return
     */
    @Transactional
    public ApiResponse<List<GroupEntity>> getGroupList() {
        List<GroupMemberEntity> groupMemberEntityList = groupMemberEntityRepo.findListByUid(authService.getId());
        List<GroupEntity> groupEntityList = new ArrayList<>();
        groupMemberEntityList.forEach(groupMemberEntity -> {
            GroupEntity group = groupEntityRepo.findByGroupId(groupMemberEntity.getGroupId());
            if (group != null) {
                groupEntityList.add(group);
            }
        });
        return ApiResponse.ok(groupEntityList);
    }

    /**
     * 获取群组信息
     *
     * @return
     */
    @Transactional
    public ApiResponse<GroupEntity> getGroupInfo(String groupId) {
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, authService.getId());
        if (member == null){
            return ApiResponse.prompt(IMCode.YOU_NOT_IN_GROUP);
        }
        GroupEntity groupEntity = groupEntityRepo.findByGroupId(groupId);
        if (groupEntity == null){
            return ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        return ApiResponse.ok(groupEntity);
    }

    /**
     * 获取群组成员列表
     *
     * @return
     */
    @Transactional
    public ApiResponse<List<GroupMemberEntity>> getGroupMemberList(String groupId) {
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, authService.getId());
        if (member == null){
            return ApiResponse.prompt(IMCode.YOU_NOT_IN_GROUP);
        }
        List<GroupMemberEntity> groupMemberEntityList = groupMemberEntityRepo.findListByGroupId(groupId);
        return ApiResponse.ok(groupMemberEntityList);
    }

    /**
     * 获取成员信息,有别于好友信息
     *
     * @return
     */
    @Transactional
    public ApiResponse<GroupMemberEntity> getGroupMember(String groupId, String uid) {
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, uid);
        if (member == null) {
            ApiResponse.prompt(IMCode.MEMBER_NOT_EXISTS);
        }
        return ApiResponse.ok(member);
    }

    /**
     * 修改群组信息
     *
     * @return
     */
    @Transactional
    public ApiResponse updateGroupInfo(String groupId, String groupName) {
        GroupEntity groupEntity = groupEntityRepo.findByUid(authService.getId(), groupId);
        if (groupEntity == null) {
            return ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        groupEntity.setGroupName(groupName);
        groupEntityRepo.save(groupEntity);
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 主动退出群组
     *
     * @return
     */
    @Transactional
    public ApiResponse exitGroup(String groupId) {
        GroupEntity group = groupEntityRepo.findByGroupId(groupId);
        if (group == null) {
            ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        //是否是成员
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, authService.getId());
        if (member == null){
            return ApiResponse.prompt(IMCode.YOU_NOT_IN_GROUP);
        }
        //是否是创建者
        if (group.getUid().equals(authService.getId())) {
            ApiResponse.prompt(IMCode.ADMIN_EXIT_BAN);
        }
        member.setTrash(true);
        groupMemberEntityRepo.save(member);
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 申请加入群组
     *
     * @return
     */
    @Transactional
    public ApiResponse askJoinGroup(String groupId, String reason) {
        //检查群组是否存在
        GroupEntity group = groupEntityRepo.findByGroupId(groupId);
        if (group == null) {
            ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        //检查是不是成员
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, authService.getId());
        if (member != null){
            return ApiResponse.prompt(IMCode.YOU_IN_GROUP);
        }
        //创建申请记录
        GroupAskJoinEntity groupAskJoinEntity = GroupAskJoinEntity.ask(groupId,authService.getId(),reason);
        groupAskJoinEntityRepo.save(groupAskJoinEntity);
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 审核待加入群组的用户
     *
     * @return
     */
    @Transactional
    public ApiResponse reviewJoinUser(String groupAskId, Boolean isAgree) {
        //校验组是不是自己创建的
        GroupAskJoinEntity groupAskJoinEntity = groupAskJoinEntityRepo.findByGroupAskId(groupAskId);
        GroupEntity group = groupEntityRepo.findByUid(authService.getId(), groupAskJoinEntity.getGroupId());
        if (group == null){
            ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        //检查是不是成员
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupAskJoinEntity.getGroupId(), groupAskJoinEntity.getUid());
        if (member != null){
            return ApiResponse.prompt(IMCode.MEMBER_EXISTS);
        }
        groupAskJoinEntity.setIsAgree(isAgree);
        groupAskJoinEntity.setIsProcess(true);
        groupAskJoinEntity = groupAskJoinEntityRepo.save(groupAskJoinEntity);
        if (groupAskJoinEntity.getIsAgree()){
            GroupMemberEntity groupMemberEntity = new GroupMemberEntity(groupAskJoinEntity.getGroupId(),groupAskJoinEntity.getUid());
            groupMemberEntityRepo.save(groupMemberEntity);
        }
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 获取申请列表
     *
     * @return
     */
    @Transactional
    public ApiResponse<List<GroupAskJoinEntity>> getReviewAskJoinGroupList() {
        //获取群组列表
        List<GroupEntity> groupEntityList = groupEntityRepo.findListByUid(authService.getId());
        List<GroupAskJoinEntity> groupAskJoinEntityList = new ArrayList<>();
        if (groupEntityList ==null){
            return ApiResponse.ok(groupAskJoinEntityList);
        }
        groupEntityList.forEach(groupEntity -> {
            List<GroupAskJoinEntity> list = groupAskJoinEntityRepo.findListByGroupId(groupEntity.getGroupId());
            if (list !=null){
                groupAskJoinEntityList.addAll(list);
            }
        });
        return ApiResponse.ok(groupAskJoinEntityList);
    }

    /**
     * 删除组内成员(T人)
     *
     * @return
     */
    @Transactional
    public ApiResponse deleteMember(String groupId, String uid) {
        GroupEntity group = groupEntityRepo.findByUid(authService.getId(), groupId);
        if (group == null) {
            ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        //防止自己删除自己
        if (group.getUid().equals(uid)) {
            ApiResponse.prompt(IMCode.ADMIN_EXIT_BAN);
        }
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, uid);
        if (member == null) {
            ApiResponse.prompt(IMCode.MEMBER_NOT_EXISTS);
        }
        member.setTrash(true);
        groupMemberEntityRepo.save(member);
        return ApiResponse.prompt(IMCode.SC_OK);
    }


    /**
     * 备注群组
     *
     * @return
     */
    @Transactional
    public ApiResponse remarkGroup(String groupId, String remark) {
        GroupMemberEntity member = groupMemberEntityRepo.findMember(groupId, authService.getId());
        if (member == null){
            return ApiResponse.prompt(IMCode.YOU_NOT_IN_GROUP);
        }
        GroupEntity groupEntity = groupEntityRepo.findByGroupId(groupId);
        if (groupEntity == null) {
            return ApiResponse.prompt(IMCode.GROUP_NOT_EXISTS);
        }
        groupEntity.setRemark(remark);
        groupEntityRepo.save(groupEntity);
        return ApiResponse.prompt(IMCode.SC_OK);
    }


    public ApiResponse search() {
        return null;
    }
}
