package com.todo.springadvancetask.repository;

import com.todo.springadvancetask.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByScheduleId(Long scheduleId);

  @Query("SELECT COALESCE(MAX(c.regId)+1, 1) "
      + "FROM Comment c "
      + "WHERE c.schedule.id =:id")
  Long findRegId(@Param("id") Long scheduleId);
}
