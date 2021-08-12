package com.dls.accountservicequery.domain.port.out.persistency

import com.dls.accountservicequery.adapter.out.AccountEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: CrudRepository<AccountEntity, UUID>