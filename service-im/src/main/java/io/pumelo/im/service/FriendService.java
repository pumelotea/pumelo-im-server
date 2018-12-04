package io.pumelo.im.service;

import com.alibaba.fastjson.JSON;
import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.FriendAskEntity;
import io.pumelo.data.im.entity.FriendEntity;
import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.data.im.repo.FriendAskEntityRepo;
import io.pumelo.data.im.repo.FriendEntityRepo;
import io.pumelo.data.im.repo.UserEntityRepo;
import io.pumelo.data.im.vo.askFriend.AskFriendVo;
import io.pumelo.data.im.vo.friend.FriendVo;
import io.pumelo.im.model.Message;
import io.pumelo.im.sender.Sender;
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
    @Autowired
    private Sender sender;

    /**
     * 申请加好友
     * @return
     */
    @Transactional
    public ApiResponse askAddFriend(String targetUid,String reason) throws Exception {
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
        //推送通知
        UserEntity me = userEntityRepo.findByUid(authService.getId());
        AskFriendVo askFriendVo = BeanUtils.copyAttrs(new AskFriendVo(),me);
        askFriendVo = BeanUtils.copyAttrs(askFriendVo,friendAskEntity);
        sender.sendToUser(targetUid,Message.makeSysMsg(targetUid, JSON.toJSONString(askFriendVo),"ASK_FRIEND","0"));
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 审核好友申请
     * @return
     */
    @Transactional
    public ApiResponse reviewAskFriend(String friendAskId,Boolean isAgree) throws Exception {
        FriendAskEntity friendAskEntity = friendAskEntityRepo.findByFriendAskId(friendAskId);
        if (friendAskEntity == null){
            return ApiResponse.prompt(IMCode.FAIL);
        }
        friendAskEntity.setIsAgree(isAgree);
        friendAskEntity.setIsProcess(true);
        friendAskEntity = friendAskEntityRepo.save(friendAskEntity);

        if (friendAskEntity.getIsAgree()){
            //检查是否是好友
            FriendEntity friendEntityMySide = friendEntityRepo.findByUidAndFriendUid(authService.getId(), friendAskEntity.getUid());
            FriendEntity friendEntityFriendSide = friendEntityRepo.findByUidAndFriendUid(friendAskEntity.getUid(),authService.getId());
            if (friendEntityMySide != null && friendEntityFriendSide !=null){
                return ApiResponse.prompt(IMCode.FRIEND_EXISTS);
            }
            //双向建立关系
            if (friendEntityMySide == null){
                friendEntityMySide = FriendEntity.makeFriend(authService.getId(),friendAskEntity.getUid());
                friendEntityRepo.save(friendEntityMySide);
            }
            //判断是否是对方的单项好友
            if (friendEntityFriendSide == null) {
                friendEntityFriendSide = FriendEntity.makeFriend(friendAskEntity.getUid(),authService.getId());
                friendEntityRepo.save(friendEntityFriendSide);
            }
            //通知推送
            UserEntity friend = userEntityRepo.findByUid(friendAskEntity.getUid());
            UserEntity me = userEntityRepo.findByUid(friendAskEntity.getTargetUid());
            sender.sendToUser(friend.getUid(),Message.makeSysMsg(friend.getUid(),"您和 "+me.getName()+" 已成为好友","TEXT","1"));
            sender.sendToUser(me.getUid(),Message.makeSysMsg(me.getUid(),"您和 "+friend.getName()+" 已成为好友","TEXT","1"));
        }
        return ApiResponse.prompt(IMCode.SC_OK);
    }

    /**
     * 获取申请列表
     * @return
     */
    public ApiResponse<List<AskFriendVo>> getReviewAskList(){
        List<FriendAskEntity> listByTargetUid = friendAskEntityRepo.findListByTargetUid(authService.getId());
        List<AskFriendVo> askFriendVos = new ArrayList<>();
        listByTargetUid.forEach(friendAskEntity -> {
            UserEntity userEntity = userEntityRepo.findByUid(friendAskEntity.getUid());
            AskFriendVo askFriendVo = BeanUtils.copyAttrs(new AskFriendVo(),userEntity);
            askFriendVo = BeanUtils.copyAttrs(askFriendVo,friendAskEntity);
            askFriendVos.add(askFriendVo);
        });
        return ApiResponse.ok(askFriendVos);
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
