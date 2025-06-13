package gabi.cast.finance_api.infra.out.repository

import gabi.cast.finance_api.domain.`in`.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CommentRepositoryImpl : JpaRepository<Comment, UUID>
