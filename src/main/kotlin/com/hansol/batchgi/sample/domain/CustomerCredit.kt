package com.hansol.batchgi.sample.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "CUSTOMER")
class CustomerCredit(
    @Id
    val id: Int,

    val name: String,

    val credit: BigDecimal,
) {

    fun increaseCreditBy(sum: BigDecimal): CustomerCredit {
        return CustomerCredit(this.id, this.name, this.credit.add(sum))
    }
}