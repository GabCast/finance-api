package gabi.cast.finance_api.infra.config

import gabi.cast.finance_api.domain.`in`.entity.Comment
import gabi.cast.finance_api.domain.`in`.entity.Member
import gabi.cast.finance_api.domain.`in`.entity.account.Account
import gabi.cast.finance_api.domain.shared.Currency
import gabi.cast.finance_api.domain.`in`.entity.activity.Activity
import gabi.cast.finance_api.domain.shared.ActivityType
import gabi.cast.finance_api.infra.out.repository.AccountAdapter
import gabi.cast.finance_api.infra.out.repository.ActivityAdapter
import gabi.cast.finance_api.infra.out.repository.CommentAdapter
import gabi.cast.finance_api.infra.out.repository.MemberAdapter
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class LoadData(
    private val accountAdapter: AccountAdapter,
    private val memberAdapter: MemberAdapter,
    private val commentAdapter: CommentAdapter,
    private val activityAdapter: ActivityAdapter
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Check if seeding is necessary

        // member
        if (memberAdapter.count() == 0L) {
            val members = listOf(
                Member(reference = "GabCast")
            )
            memberAdapter.saveAll(members)
            println("✅ Seeded members!")
        }

        // account
        if (accountAdapter.count() == 0L) {
            val member = memberAdapter.findAll().first()
            val accounts = listOf(
                Account(member = member, name = "Wise", currency = Currency.USD),
                Account(member = member, name = "Deel", currency = Currency.USD),
                Account(member = member, name = "N26", currency = Currency.EUR),
                Account(member = member, name = "Cash", currency = Currency.USD),
            )
            accountAdapter.saveAll(accounts)
            println("✅ Seeded accounts!")
        }

        // activities with and without comments
        if (activityAdapter.count() == 0L) {
            val activities = listOf(
                Activity(account = accountAdapter.findByName("Deel")!!, amount = 5000.0, type = ActivityType.CREDIT),
                Activity(
                    account = accountAdapter.findByName("Wise")!!,
                    amount = 40000.0,
                    type = ActivityType.CREDIT,
                    paid = true
                ),
                Activity(
                    account = accountAdapter.findByName("Wise")!!,
                    amount = 100.0,
                    type = ActivityType.DEBIT,
                    paid = true
                ),
                Activity(account = accountAdapter.findByName("Cash")!!, amount = 5000.0, type = ActivityType.CREDIT, paid = true),
                Activity(account = accountAdapter.findByName("N26")!!, amount = 18.67, type = ActivityType.CREDIT, paid = true),
            )
            activityAdapter.saveAll(activities)
            val activitySaved = activityAdapter.save(
                Activity(
                    account = accountAdapter.findByName("Wise")!!,
                    amount = 5000.0,
                    type = ActivityType.DEBIT
                )
            )
            println("✅ Seeded activities!")

            // comments
            if (commentAdapter.count() == 0L) {
                val comments = listOf(
                    Comment(text = "Transfer to cash", activity = activitySaved),
                    Comment(text = "Transfer to cash 2", activity = activitySaved),
                )
                commentAdapter.saveAll(comments)
                println("✅ Seeded comments!")
            }
        }
    }
}