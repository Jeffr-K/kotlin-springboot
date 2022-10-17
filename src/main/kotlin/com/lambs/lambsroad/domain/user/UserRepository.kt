package com.lambs.lambsroad.domain.user

import org.springframework.data.repository.CrudRepository
import java.util.*


interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}
