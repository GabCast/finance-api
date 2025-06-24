package gabi.cast.finance_api.domain.`in`

import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import java.util.UUID

interface CommentService {
    fun findByActivityId(id: UUID): ActionResult<List<Comment?>>
    fun save(commentDomain: CommentDomain) : ActionResult<Comment>
    fun update(id: UUID, text: String) : ActionResult<Comment>
    fun delete(id: UUID) : ActionResult<Unit>
}