package gabi.cast.finance_api.domain.`in`.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Timestamp
import java.util.UUID

@Entity
@Table(name = "comment", schema = "public")
data class Comment(

    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    val text: String,

    @JsonBackReference("activity-comments") // hijo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    val activity: Activity,

    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

    val updatedAt: Timestamp? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Comment
        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    override fun toString(): String {
        return "Comment(id=$id, text=$text, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
