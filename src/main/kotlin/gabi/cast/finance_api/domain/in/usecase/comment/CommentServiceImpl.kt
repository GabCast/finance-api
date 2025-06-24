package gabi.cast.finance_api.domain.`in`.usecase.comment

import gabi.cast.finance_api.domain.`in`.CommentService
import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.toActionResultIfNotEmpty
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

    override fun findByActivityId(id: UUID): ActionResult<List<Comment?>> = runCatching {
        commentAdapter.findByActivityId(id)
    }.fold(
        onSuccess = { optional ->
            optional.toActionResultIfNotEmpty(ErrorResult.CommentsForActivityNotFound)
        },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Found comments for activity id: $id")
        println("Result: $it")
    }

    override fun save(commentDomain: CommentDomain): ActionResult<Comment> = runCatching {
        // get activity
        val activitySaved = activitiesAdapter.findById(commentDomain.activityId).orElse(null)
            ?: return ActionResult.Error(ErrorResult.ActivityNotFound)

        //save comment
        val comment = Comment(text = commentDomain.text, activity = activitySaved)
        commentAdapter.save(comment)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Saved comment: $it")
    }

    override fun update(id: UUID, text: String): ActionResult<Comment> = runCatching {
        // get comment
        val comment = commentAdapter.findById(id).orElse(null)
            ?: return ActionResult.Error(ErrorResult.CommentsForActivityNotFound)

        // update the row
        val commentUpdated = comment.copy(text = text, updatedAt = Timestamp.from(Instant.now()))

        // updated
        commentAdapter.save(commentUpdated)
    }.fold(
        onSuccess = { ActionResult.Success(it) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Saved comment: $it")
    }


    override fun delete(id: UUID): ActionResult<Unit> = runCatching {
        if (!commentAdapter.existsById(id)) {
            return ActionResult.Error(ErrorResult.CommentsForActivityNotFound)
        }
        commentAdapter.deleteById(id)
    }.fold(
        onSuccess = { ActionResult.Success(Unit) },
        onFailure = { ActionResult.Error(ErrorResult.InternalServerError) }
    ).also {
        println("Deleted comment: $id")
    }
}
