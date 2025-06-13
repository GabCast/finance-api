package gabi.cast.finance_api.domain.`in`.entity.account

import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.shared.Currency
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Timestamp
import java.util.UUID

@Entity
@Table(name = "account", schema = "public", indexes = [Index(name = "idx_account_name", columnList = "name")])
data class Account(

    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // nombre de la columna FK en la tabla
    val member: Member,

    @Column(unique = true)
    val name: String,

    @Enumerated(EnumType.STRING)
    val currency: Currency,

    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

    val updatedAt: Timestamp? = null,
)