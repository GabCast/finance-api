package gabi.cast.finance_api.infra.out.rest

import gabi.cast.finance_api.domain.`in`.entity.Comment
import java.util.UUID

data class CommentDTO(
    val id: UUID?,
    val text: String,
    val activityId: UUID?,
    val createdAt: String,
    val updatedAt: String?
)

fun Comment.toDTO(): CommentDTO = CommentDTO(
    id = this.id,
    text = this.text,
    activityId = this.activity.id,
    createdAt = this.createdAt.toString(),
    updatedAt = this.updatedAt?.toString()
)
