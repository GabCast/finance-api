package gabi.cast.finance_api.domain.`in`.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "member", schema = "public")
data class Member(

    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    val reference: String,
)
