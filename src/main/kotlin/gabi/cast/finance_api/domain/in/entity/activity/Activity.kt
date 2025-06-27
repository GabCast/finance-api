package gabi.cast.finance_api.domain.`in`.entity.activity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.ActivityType
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.sql.Timestamp
import java.util.UUID

@Entity
@Table(name = "activity", schema = "public")
data class Activity(

    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference("account-activities") // hijo
    val account: Account,

    val amount: Double,

    @Enumerated(EnumType.STRING)
    val type: ActivityType,

    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

    val paid: Boolean = false,

    @OneToMany(mappedBy = "activity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("activity-comments") // padre
    val comments: List<Comment>? = null,

    val updatedAt: Timestamp? = null,
) {
    override fun toString(): String {
        return "Activity(id=$id, amount=$amount, type=$type, paid=$paid)"
    }
}
