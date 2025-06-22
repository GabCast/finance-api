package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.CommentService
import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.shared.ActionResult
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import gabi.cast.finance_api.infra.`in`.rest.resource.BaseResponseBody
import gabi.cast.finance_api.infra.`in`.rest.resource.ErrorResponse
import gabi.cast.finance_api.infra.out.rest.CommentDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.util.UUID

@RestController
@RequestMapping("/comment")
class CommentController (private val commentService: CommentService) {

    @Operation(summary = "comment section")
    @GetMapping("/activity/{activityId}")
    fun commentsByActivity(@PathVariable(required = true) activityId: UUID) : ResponseEntity<*> {
        return when (val actionResult =
            commentService.findByActivityId(activityId)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<List<Comment>, List<CommentDTO>>(actionResult)
            { comments -> comments?.map { it.toDTO() } }.asEntity(successHttpStatus = HttpStatus.OK)
        }
    }

    @Operation(
        summary = "Create a new comment",
        description = "Creates a new comment with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Comment to be created",
            required = true,
            content = [Content(schema = Schema(implementation = CommentDomain::class))]
        )
    )
    @PostMapping()
    fun create(@RequestBody(required = true) comment: CommentDomain) : ResponseEntity<*> {

        return when (val actionResult =
            commentService.save(comment)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Comment, CommentDTO>(actionResult)
            { it?.toDTO() }.asEntity(successHttpStatus = HttpStatus.CREATED)
        }
    }

    @PutMapping("/{commentId}")
    fun update(@PathVariable(required = true) commentId: UUID, @RequestBody(required = true) comment: String) : ResponseEntity<*> {

        return when (val actionResult =
            commentService.update(commentId, comment)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Comment, CommentDTO>(actionResult)
            { it?.toDTO() }.asEntity(successHttpStatus = HttpStatus.OK)
        }
    }

    @DeleteMapping("/{commentId}")
    fun delete(@PathVariable(required = true) commentId: UUID) : ResponseEntity<*> {

        return when (val actionResult =
            commentService.delete(commentId)) {
            is ActionResult.Error -> {
                val errorResponse =
                    ErrorResponse.from(
                        actionResult.error
                    )
                ResponseEntity.status(errorResponse.httpStatusCode).body(errorResponse)
            }

            is ActionResult.Success -> BaseResponseBody.from<Unit>(actionResult).asEntity(successHttpStatus = HttpStatus.OK)
        }
    }
}
