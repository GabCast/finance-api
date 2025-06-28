package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.CommentService
import gabi.cast.finance_api.domain.shared.ErrorResult
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.domain.shared.mapUnit
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
import gabi.cast.finance_api.infra.out.rest.CommentDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
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
class CommentController(private val commentService: CommentService) {

    @Operation(
        summary = "comments by activity id", description = "Returns all comments", responses = [
            ApiResponse(
                responseCode = "200", description = "Comments returned successfully", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid activity id provided. Must be a UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACTIVITY NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.ActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @GetMapping("/activity/{activityId}")
    fun commentsByActivity(@PathVariable(required = true) activityId: UUID): ResponseEntity<*> =
        commentService.findByActivityId(activityId).mapList(transform = { it?.toDTO() }, successStatus = HttpStatus.OK)

    @Operation(
        summary = "Create a new comment",
        description = "Creates a new comment with the specified data.",
        requestBody = SwaggerRequestBody(
            description = "Comment to be created",
            required = true,
            content = [Content(schema = Schema(implementation = CommentDomain::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Comment created successfully. Returned comment data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid comment data provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "ACTIVITY NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.ActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    @PostMapping()
    fun create(@Valid @RequestBody(required = true) comment: CommentDomain): ResponseEntity<*> =
        commentService.save(comment).map(transform = { it.toDTO() }, successStatus = HttpStatus.CREATED)


    @PutMapping("/{commentId}")
    @Operation(
        summary = "Update a comment",
        description = "Update a comment with the specified data.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Comment updated successfully. Returned comment data",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid comment data provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "COMMENT NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.CommentsForActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    fun update(
        @PathVariable(required = true) commentId: UUID,
        @RequestBody(required = true) comment: String
    ): ResponseEntity<*> =
        commentService.update(commentId, comment).map(transform = { it.toDTO() }, successStatus = HttpStatus.OK)

    @DeleteMapping("/{commentId}")
    @Operation(
        summary = "Delete a comment",
        description = "Delete a comment with the specified data.",
        responses = [
            ApiResponse(responseCode = "200", description = "Comment deleted successfully."),
            ApiResponse(
                responseCode = "400",
                description = "Invalid comment id provided. Must be a valid UUID with hyphens, without dashes or spaces."
            ),
            ApiResponse(
                responseCode = "404", description = "COMMENT NOT FOUND", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.CommentsForActivityNotFound::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResult.InternalServerError::class)
                )]
            )
        ]
    )
    suspend fun delete(@PathVariable(required = true) commentId: UUID): ResponseEntity<*> =
        commentService.delete(commentId).mapUnit(successStatus = HttpStatus.NO_CONTENT)
}
