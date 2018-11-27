package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.MessageEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageEntityRepo extends BaseDao<MessageEntity, Long> {

    @Query("select m from MessageEntity m where m.from=:send_from and m.to=:to and  m.isSent = false and (m.msgType = 'USER' or m.msgType = 'GROUP')")
    List<MessageEntity> findListByfrom(@Param("send_from") String from,@Param("to") String to);

}
