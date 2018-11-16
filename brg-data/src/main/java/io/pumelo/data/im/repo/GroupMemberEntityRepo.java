package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.GroupMemberEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberEntityRepo extends BaseDao<GroupMemberEntity,String> {

    /**
     * 标记删除
     * @param groupId
     * @return
     */
    @Query("update GroupMemberEntity m set m.isTrash = true where m.groupId=:groupId and m.isTrash = false ")
    int trashByGroupIdAndUid(@Param("groupId") String groupId);

    /**
     * 查单个成员信息
     * @param groupId
     * @param uid
     * @return
     */
    @Query("select m from GroupMemberEntity m where  m.groupId=:groupId and m.uid=:uid and m.isTrash = false")
    GroupMemberEntity findMember(@Param("groupId") String groupId,@Param("uid") String uid);


    /**
     * 根据用户id查群组列表
     * @param uid
     * @return
     */
    @Query("select m from GroupMemberEntity m where m.uid = :uid and m.isTrash=false")
    List<GroupMemberEntity> findListByUid(@Param("uid") String uid);


    /**
     * 根据群组id查成员列表
     * @param groupId
     * @return
     */
    @Query("select m from GroupMemberEntity m where m.groupId = :groupId and m.isTrash=false")
    List<GroupMemberEntity> findListByGroupId(@Param("groupId") String groupId);

}
