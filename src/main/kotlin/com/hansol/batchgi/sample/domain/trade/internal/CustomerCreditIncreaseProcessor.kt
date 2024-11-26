package com.hansol.batchgi.sample.domain.trade.internal

import com.hansol.batchgi.sample.domain.CustomerCredit
import org.springframework.batch.item.ItemProcessor
import java.math.BigDecimal

class CustomerCreditIncreaseProcessor : ItemProcessor<CustomerCredit, CustomerCredit> {

    companion object {
        val FIXED_AMOUNT = BigDecimal("5")
    }

    override fun process(item: CustomerCredit): CustomerCredit {
        return item.increaseCreditBy(FIXED_AMOUNT)
    }
}