package gabi.cast.finance_api.infra.`in`.rest.controller

import gabi.cast.finance_api.infra.out.rest.toDTO
import gabi.cast.finance_api.domain.`in`.CommentService
import gabi.cast.finance_api.domain.shared.map
import gabi.cast.finance_api.domain.shared.mapList
import gabi.cast.finance_api.domain.shared.mapUnit
import gabi.cast.finance_api.infra.`in`.rest.model.CommentDomain
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
class CommentController(private val commentService: CommentService) {

    @Operation(summary = "comment section")
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
        )
    )
    @PostMapping()
    fun create(@RequestBody(required = true) comment: CommentDomain): ResponseEntity<*> =
        commentService.save(comment).map(transform = { it.toDTO() }, successStatus = HttpStatus.CREATED)


    @PutMapping("/{commentId}")
    fun update(
        @PathVariable(required = true) commentId: UUID,
        @RequestBody(required = true) comment: String
    ): ResponseEntity<*> =
        commentService.update(commentId, comment).map(transform = { it.toDTO() }, successStatus = HttpStatus.OK)

    @DeleteMapping("/{commentId}")
    suspend fun delete(@PathVariable(required = true) commentId: UUID): ResponseEntity<*> =
        commentService.delete(commentId).mapUnit(successStatus = HttpStatus.NO_CONTENT)
}
