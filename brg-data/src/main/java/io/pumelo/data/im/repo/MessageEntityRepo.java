package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.MessageEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageEntityRepo extends BaseDao<MessageEntity, Long> {

    /**
     * 查询单个用户的离线消息分页
     * @param from
     * @param to
     * @param pageable
     * @return
     */
    @Query("select m from MessageEntity m where m.from=:send_from and m.to=:to and  m.isSent = false and (m.msgType = 'USER' or m.msgType = 'GROUP')")
    List<MessageEntity> findListByfrom(@Param("send_from") String from, @Param("to") String to, Pageable pageable);


    /**
     * 统计离线消息的数量
     * @param from
     * @param to
     * @return
     */
    @Query("select count(m) from MessageEntity  m where m.from=:send_from and m.to=:to and  m.isSent = false and (m.msgType = 'USER' or m.msgType = 'GROUP')")
    int countByFrom(@Param("send_from") String from, @Param("to") String to);

    @Query("select m.from from MessageEntity m where m.to = :to ")
    List<String> findFromList(@Param("to") String to);

    @Query(nativeQuery = true,value = "select * from (select * from im_message where send_to=:send_to and is_sent = 0 and (msg_type = 'USER' or msg_type= 'GROUP' )order by  message_id desc ) as a group by send_from")
    List<MessageEntity> findListGroupByFrom(@Param("send_to") String to);

}
