package gabi.cast.finance_api.infra.config

import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.Currency
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActivityType
import gabi.cast.finance_api.infra.out.repository.AccountRepositoryImpl
import gabi.cast.finance_api.infra.out.repository.ActivityRepositoryImpl
import gabi.cast.finance_api.infra.out.repository.CommentRepositoryImpl
import gabi.cast.finance_api.infra.out.repository.MemberRepositoryImpl
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class LoadData(
    private val accountRepositoryImpl: AccountRepositoryImpl,
    private val memberRepositoryImpl: MemberRepositoryImpl,
    private val commentRepositoryImpl: CommentRepositoryImpl,
    private val activityRepositoryImpl: ActivityRepositoryImpl
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Check if seeding is necessary

        // member
        if (memberRepositoryImpl.count() == 0L) {
            val members = listOf(
                Member(reference = "GabCast")
            )
            memberRepositoryImpl.saveAll(members)
            println("✅ Seeded members!")
        }

        // account
        if (accountRepositoryImpl.count() == 0L) {
            val accounts = listOf(
                Account(member = memberRepositoryImpl.findAll().first(), name = "Wise", currency = Currency.USD),
                Account(member = memberRepositoryImpl.findAll().first(), name = "Deel", currency = Currency.USD),
                Account(member = memberRepositoryImpl.findAll().first(), name = "N26", currency = Currency.EUR),
                Account(member = memberRepositoryImpl.findAll().first(), name = "Cash", currency = Currency.USD),
            )
            accountRepositoryImpl.saveAll(accounts)
            println("✅ Seeded accounts!")
        }

        // activities with and without comments
        if (activityRepositoryImpl.count() == 0L) {
            val activities = listOf(
                Activity(account = accountRepositoryImpl.findByName("Deel")!!, amount = 5000.0, type = ActivityType.CREDIT),
                Activity(
                    account = accountRepositoryImpl.findByName("Wise")!!,
                    amount = 40000.0,
                    type = ActivityType.CREDIT
                ),
                Activity(account = accountRepositoryImpl.findByName("Cash")!!, amount = 5000.0, type = ActivityType.CREDIT),
                Activity(account = accountRepositoryImpl.findByName("N26")!!, amount = 18.67, type = ActivityType.CREDIT),
            )
            activityRepositoryImpl.saveAll(activities)
            val activitySaved = activityRepositoryImpl.save(
                Activity(
                    account = accountRepositoryImpl.findByName("Wise")!!,
                    amount = 5000.0,
                    type = ActivityType.DEBIT
                )
            )
            println("✅ Seeded activities!")

            // comments
            if (commentRepositoryImpl.count() == 0L) {
                val comments = listOf(
                    Comment(text = "Transfer to cash", activity = activitySaved),
                    Comment(text = "Transfer to cash 2", activity = activitySaved),
                )
                commentRepositoryImpl.saveAll(comments)
                println("✅ Seeded comments!")
            }
        }
    }
}