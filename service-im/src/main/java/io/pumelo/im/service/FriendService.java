package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendAskEntity;
import io.pumelo.data.im.entity.FriendEntity;
import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.data.im.repo.FriendAskEntityRepo;
import io.pumelo.data.im.repo.FriendEntityRepo;
import io.pumelo.data.im.repo.UserEntityRepo;
import io.pumelo.data.im.vo.friend.FriendVo;
import io.pumelo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendEntityRepo friendEntityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private FriendAskEntityRepo friendAskEntityRepo;
    @Autowired
    private AuthService authService;

    /**
     * 申请加好友
     * @return
     */
    @Transactional
    public ApiResponse askAddFriend(String targetUid,String reason){
        //检查用户是否纯在
        UserEntity userEntity = userEntityRepo.findByUid(targetUid);
        if (userEntity == null){
            return ApiResponse.prompt(IMCode.ACCOUNT_NOT_EXISTS);
        }
        //检查是否是好友
        FriendEntity friendEntity = friendEntityRepo.findByUidAndFriendUid(authService.getId(), targetUid);
        if (friendEntity != null){
            return ApiResponse.prompt(IMCode.FRIEND_EXISTS);
        }
        FriendAskEntity friendAskEntity = FriendAskEntity.ask(authService.getId(),targetUid,reason);
        friendAskEntityRepo.save(friendAskEntity);
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 审核好友申请
     * @return
     */
    @Transactional
    public ApiResponse reviewAskFriend(String friendAskId,Boolean isAgree){
        FriendAskEntity friendAskEntity = friendAskEntityRepo.findByFriendAskId(friendAskId);
        if (friendAskEntity == null){
            return ApiResponse.prompt(IMCode.FAIL);
        }
        //检查是否是好友
        FriendEntity friendEntityMySide = friendEntityRepo.findByUidAndFriendUid(authService.getId(), friendAskEntity.getTargetUid());
        if (friendEntityMySide != null){
            return ApiResponse.prompt(IMCode.FRIEND_EXISTS);
        }


        friendAskEntity.setIsAgree(isAgree);
        friendAskEntity.setIsProcess(true);
        friendAskEntity = friendAskEntityRepo.save(friendAskEntity);
        if (friendAskEntity.getIsAgree()){
            //双向建立关系
            FriendEntity friendEntityFriendSide = friendEntityRepo.findByUidAndFriendUid(friendAskEntity.getTargetUid(),authService.getId());
            FriendEntity friendEntityTo = FriendEntity.makeFriend(friendAskEntity.getUid(),friendAskEntity.getTargetUid());
            friendEntityRepo.save(friendEntityTo);
            //判断是否是对方的单项好友
            if (friendEntityFriendSide == null) {
                FriendEntity friendEntityFrom = FriendEntity.makeFriend(friendAskEntity.getTargetUid(),friendAskEntity.getUid());
                friendEntityRepo.save(friendEntityFrom);
            }
        }
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 获取申请列表
     * @return
     */
    public ApiResponse<List<FriendAskEntity>> getReviewAskList(){
        return ApiResponse.ok(friendAskEntityRepo.findListByTargetUid(authService.getId()));
    }

    /**
     * 删除好友
     * @return
     */
    @Transactional
    public ApiResponse deleteFriend(String friendUid,Boolean isBoth){
        FriendEntity friendEntity = friendEntityRepo.findByUidAndFriendUid(authService.getId(), friendUid);
        if (friendEntity == null){
            return ApiResponse.prompt(IMCode.FRIEND_NOT_EXISTS);
        }
        friendEntity.setTrash(true);
        friendEntityRepo.save(friendEntity);
        if (isBoth){
            FriendEntity friendSide = friendEntityRepo.findByUidAndFriendUid(friendUid,authService.getId());
            if (friendSide != null){
                friendSide.setTrash(true);
                friendEntityRepo.save(friendSide);
            }
        }
        return ApiResponse.prompt(IMCode.SC_OK);
    }



    /**
     * 获取好友列表
     * @return
     */
    @Transactional
    public ApiResponse<List<FriendVo>> getFriendList(){
        List<FriendEntity> friendEntityList = friendEntityRepo.findListByUidAndFriendUid(authService.getId());
        List<FriendVo> friendVoList = new ArrayList<>();
        if (friendEntityList== null){
            return ApiResponse.ok(friendVoList);
        }
        friendEntityList.forEach(friendEntity -> {
            UserEntity userEntity = userEntityRepo.findByUid(friendEntity.getFriendUid());
            FriendVo friendVo = BeanUtils.copyAttrs(new FriendVo(),userEntity);
            friendVo.setRemark(friendEntity.getRemark());
            friendVoList.add(friendVo);
        });
        return ApiResponse.ok(friendVoList);
    }

    /**
     * 获取单个好友详情
     * @return
     */
    public ApiResponse<FriendVo> getFriend(String uid){
        FriendEntity friendEntity = friendEntityRepo.findByUidAndFriendUid(authService.getId(), uid);
        if (friendEntity == null){
            return ApiResponse.prompt(IMCode.FRIEND_NOT_EXISTS);
        }
        UserEntity userEntity = userEntityRepo.findByUid(uid);
        FriendVo friendVo = BeanUtils.copyAttrs(new FriendVo(),userEntity);
        friendVo.setRemark(friendEntity.getRemark());
        return ApiResponse.ok(friendVo);
    }


    /**
     * 备注好友
     * @return
     */
    @Transactional
    public ApiResponse<FriendVo> remarkFriend(String uid,String remark){
        FriendEntity friendEntity = friendEntityRepo.findByUidAndFriendUid(authService.getId(), uid);
        if (friendEntity == null){
            return ApiResponse.prompt(IMCode.FRIEND_NOT_EXISTS);
        }
        friendEntity.setRemark(remark);
        friendEntity = friendEntityRepo.save(friendEntity);
        UserEntity userEntity = userEntityRepo.findByUid(uid);
        FriendVo friendVo = BeanUtils.copyAttrs(new FriendVo(),userEntity);
        friendVo.setRemark(friendEntity.getRemark());
        return ApiResponse.ok(friendVo);
    }


}
