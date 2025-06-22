package gabi.cast.finance_api.domain.`in`.usecase.comment

import gabi.cast.finance_api.domain.`in`.CommentService
import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import gabi.cast.finance_api.infra.out.repository.ActivityAdapter
import gabi.cast.finance_api.infra.out.repository.CommentAdapter
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

@Service
class CommentServiceImpl(
    private val commentAdapter: CommentAdapter,
    private val activitiesAdapter: ActivityAdapter,
) : CommentService {

    override fun findByActivityId(id: UUID): ActionResult {
       val result = commentAdapter.findByActivityId(id)
        return when {
           result.isEmpty() -> ActionResult.Error(ErrorResult.CommentsForActivityNotFound)
           else -> ActionResult.Success(result)
        }
    }

    override fun save(commentDomain: CommentDomain): ActionResult {
        // get activity
        val activitySaved = activitiesAdapter.findById(commentDomain.activityId).orElse(null)
            ?: return ActionResult.Error(ErrorResult.ActivityNotFound)

        //save comment
        val comment = Comment(text = commentDomain.text, activity = activitySaved)

        return ActionResult.Success(commentAdapter.save(comment))
    }

    override fun update(id: UUID, text: String): ActionResult {
        // get comment
        val comment = commentAdapter.findById(id).orElse(null)
            ?: return ActionResult.Error(ErrorResult.CommentsForActivityNotFound)

        // update the row
        val commentUpdated = comment.copy(text = text, updatedAt = Timestamp.from(Instant.now()))

        // updated
        return ActionResult.Success(commentAdapter.save(commentUpdated))
    }

    override fun delete(id: UUID): ActionResult {
        return try {
            if (!commentAdapter.existsById(id)) {
                return ActionResult.Error(ErrorResult.CommentsForActivityNotFound)
            }
            commentAdapter.deleteById(id)
            ActionResult.Success(Unit)
        } catch (ex: Exception) {
            println("Error deleting comment: ${ex.message}")
            ActionResult.Error(ErrorResult.InternalServerError)
        }
    }
}
