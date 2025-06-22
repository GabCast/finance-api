package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.out.repository.CommentRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CommentAdapter : CommentRepository {
    fun findByActivityId(activityId: UUID): List<Comment?>
}